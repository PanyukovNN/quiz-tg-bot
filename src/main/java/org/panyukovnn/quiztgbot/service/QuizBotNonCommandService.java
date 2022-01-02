package org.panyukovnn.quiztgbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.panyukovnn.quiztgbot.model.*;
import org.panyukovnn.quiztgbot.repository.QuizRepository;
import org.panyukovnn.quiztgbot.service.questionprocessor.AnswerProcessInfo;
import org.panyukovnn.quiztgbot.service.questionprocessor.AnswerProcessor;
import org.panyukovnn.quiztgbot.service.questionprocessor.AnswerProcessorResolver;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.panyukovnn.quiztgbot.model.Constants.PRESENT;
import static org.panyukovnn.quiztgbot.model.Constants.TWELVE_O_CLOCK;

/**
 * Обработка простых сообщений пользователя
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QuizBotNonCommandService {

    private final Quiz quiz;
    private final QuizRepository quizRepository;
    private final DateTimeService dateTimeService;
    private final QuizBotMessageExecutor quizBotMessageExecutor;
    private final AnswerProcessorResolver answerProcessorResolver;

    public final AtomicBoolean lock = new AtomicBoolean();

    //TODO remove
    private boolean veryFirstStart = true;

    /**
     * Обработка сообщения пользователя
     *
     * @param chatId идентификатор чата
     * @param messageText текст сообщения пользователя
     */
    public void processMessage(String chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (!dateTimeService.is2022Year()) {
            String tooEarlyMessage = defineTooEarlyMessage();
            sendMessage.setText(tooEarlyMessage);

            quizBotMessageExecutor.executeSendMessage(sendMessage);

            return;
        }

        int offset = quiz.getOffset();
        if (offset >= quiz.getQuestionList().size()) {
            return;
        }

        Question question = quiz.getQuestionList().get(offset);

        if (askQuestion(sendMessage, question)) {
            question.setAsked(true);
            quizRepository.save(quiz);

            if (question.getAnswer().getType() == AnswerType.SPECIAL_WITH_IMG) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(new InputFile(new File("./photo.jpg")));
                sendPhoto.setReplyMarkup(sendMessage.getReplyMarkup());

                sendMessage.setReplyMarkup(null);
                quizBotMessageExecutor.executeSendMessage(sendMessage);

                quizBotMessageExecutor.executeSendPhoto(sendPhoto);
            } else {
                quizBotMessageExecutor.executeSendMessage(sendMessage);
            }

            return;
        }

        Answer answer = question.getAnswer();

        AnswerProcessor answerProcessor = answerProcessorResolver.getProcessor(answer.getType());

        AnswerProcessInfo answerProcessInfo = answerProcessor.process(messageText, answer);
        if (StringUtils.isNotBlank(answerProcessInfo.getTextToReturn())) {
            sendMessage.setText(answerProcessInfo.getTextToReturn());
            quizBotMessageExecutor.executeSendMessage(sendMessage);
        }

        quiz.setOffset(quiz.getOffset() + 1);
        quizRepository.save(quiz);

        if (answerProcessInfo.isNeedNextQuestion()) {
            sleepSafe();
            int nextOffset = quiz.getOffset();
            if (nextOffset >= quiz.getQuestionList().size()) {
                return;
            }
            Question nextQuestion = quiz.getQuestionList().get(nextOffset);
            askQuestion(sendMessage, nextQuestion);
            nextQuestion.setAsked(true);
            quizRepository.save(quiz);

            if (nextQuestion.getAnswer().getType() == AnswerType.SPECIAL_WITH_IMG) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(new InputFile(new File("./photo.jpg")));
                sendPhoto.setReplyMarkup(sendMessage.getReplyMarkup());

                sendMessage.setReplyMarkup(null);
                quizBotMessageExecutor.executeSendMessage(sendMessage);

                quizBotMessageExecutor.executeSendPhoto(sendPhoto);
            } else {
                quizBotMessageExecutor.executeSendMessage(sendMessage);
            }
        }
    }

    private String defineTooEarlyMessage() {
        if (veryFirstStart) {
            veryFirstStart = false;

            return  "Привет! Давай-ка посмотрим который сейчас год... 2021?" +
                    " Извини, Дед Мороз поставил меня на блокировку до 2022 года, приходи сразу после боя курантов " + TWELVE_O_CLOCK +
                    " И мы вместе выберем тебе подарок " + PRESENT;
        }

        return "Пока рано, надо дождаться Нового Года...";
    }

    private boolean askQuestion(SendMessage sendMessage, Question question) {
        if (!question.isAsked()) {
            sendMessage.setText(question.getText());
            createKeyboard(question).ifPresent(sendMessage::setReplyMarkup);

            return true;
        }

        return false;
    }

    private Optional<ReplyKeyboard> createKeyboard(Question question) {
        if (question.getAnswer().getType() == AnswerType.MULTI_OPTION
                || question.getAnswer().getType() == AnswerType.SINGLE_OPTION
                || question.getAnswer().getType() == AnswerType.NO_MATTER_OPTION
                || question.getAnswer().getType() == AnswerType.SPECIAL_WITH_IMG) {

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

    private void sleepSafe() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
