package org.panyukovnn.quiztgbot.service;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.model.*;
import org.panyukovnn.quiztgbot.repository.QuizRepository;
import org.panyukovnn.quiztgbot.service.questionprocessor.AnswerProcessInfo;
import org.panyukovnn.quiztgbot.service.questionprocessor.AnswerProcessor;
import org.panyukovnn.quiztgbot.service.questionprocessor.AnswerProcessorResolver;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.panyukovnn.quiztgbot.model.Constants.*;

/**
 * Обработка простых сообщений пользователя
 */
@Service
@RequiredArgsConstructor
public class QuizBotNonCommandService {

    private final Quiz quiz;
    private final QuizRepository quizRepository;
    private final DateTimeService dateTimeService;
    private final AnswerProcessorResolver answerProcessorResolver;

    public final AtomicBoolean lock = new AtomicBoolean();

    //TODO remove
    private boolean veryFirstStart = true;

    /**
     * Обработка сообщения пользователя
     *
     * @param message сообщение пользователя
     */
    public SendMessage processMessage(Message message) throws InterruptedException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());

        if (!dateTimeService.is2022Year()) {
            String tooEarlyMessage = defineTooEarlyMessage();
            sendMessage.setText(tooEarlyMessage);

            return sendMessage;
        }

        int offset = quiz.getOffset();
        if (offset >= quiz.getQuestionList().size()) {
            return sendMessage;
        }

        Question question = quiz.getQuestionList().get(offset);

        boolean questionAsked = askQuestion(sendMessage, question);

        if (questionAsked) {
            return sendMessage;
        }

        Answer answer = question.getAnswer();

        AnswerProcessor answerProcessor = answerProcessorResolver.getProcessor(answer.getType());

        AnswerProcessInfo answerProcessInfo = answerProcessor.process(message.getText(), answer);
        sendMessage.setText(answerProcessInfo.getTextToReturn());

        if (answerProcessInfo.isNeedNextQuestion()) {
            Thread.sleep(1000);
            int nextOffset = quiz.getOffset();
            if (nextOffset >= quiz.getQuestionList().size()) {
                return sendMessage;
            }
            Question nextQuestion = quiz.getQuestionList().get(nextOffset);
            askQuestion(sendMessage, nextQuestion);
        }

        return sendMessage;
    }

    private String defineTooEarlyMessage() {
        if (veryFirstStart) {
            veryFirstStart = false;

            return  "Ура, ты нашла меня! " + CONGRATULATION_FACE +
                    " Давай-ка посмотрим который сейчас год... Хмм, 2021..." +
                    " Хозяин поставил на меня блокировку до 2022 года, приходи сразу после боя курантов " + TWELVE_O_CLOCK +
                    " И мы вместе найдём подарок " + PRESENT;
        }

        return "Пока рано, надо дождаться Нового Года...";
    }

    private boolean askQuestion(SendMessage sendMessage, Question question) {
        if (!question.isAsked()) {
            question.setAsked(true);
            quizRepository.save(quiz);

            sendMessage.setText(question.getText());
            createKeyboard(question).ifPresent(sendMessage::setReplyMarkup);

            return true;
        }

        return false;
    }

    private Optional<ReplyKeyboard> createKeyboard(Question question) {
        if (question.getAnswer().getType() == AnswerType.MULTI_OPTION
                || question.getAnswer().getType() == AnswerType.SINGLE_OPTION
                || question.getAnswer().getType() == AnswerType.NO_MATTER_OPTION) {

            if (question.getAnswer().getOptions().isEmpty()) {
                return Optional.empty();
            }

            InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();

            List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();

            for (Option option : question.getAnswer().getOptions()) {
                List<InlineKeyboardButton> buttonRow = new ArrayList<>();

                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(option.getText());
                inlineKeyboardButton.setCallbackData(option.getText());

                buttonRow.add(inlineKeyboardButton);
                buttonRows.add(buttonRow);
            }

            inlineKeyboardMarkup.setKeyboard(buttonRows);

            return Optional.of(inlineKeyboardMarkup);
        }

        return Optional.empty();
    }
}
