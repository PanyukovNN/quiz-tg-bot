package org.panyukovnn.quiztgbot.service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.panyukovnn.quiztgbot.exception.QuizTgBotException;
import org.panyukovnn.quiztgbot.model.Answer;
import org.panyukovnn.quiztgbot.model.AnswerType;
import org.panyukovnn.quiztgbot.model.Question;
import org.panyukovnn.quiztgbot.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.panyukovnn.quiztgbot.model.Constants.*;

/**
 * Обработка простых сообщений пользователя
 */
@Service
@RequiredArgsConstructor
public class QuizBotNonCommandService {

    private final Quiz quiz;
    private final DateTimeService dateTimeService;

    public final AtomicBoolean lock = new AtomicBoolean();

    //TODO remove
    private boolean veryFirstStart = true;

    /**
     * Обработка сообщения пользователя
     *
     * @param messageText текст сообщения пользователя
     */
    public void processMessage(String messageText, Consumer<String> sendMessage) throws InterruptedException {
        if (!dateTimeService.is2022Year()) {
            if (veryFirstStart) {
                veryFirstStart = false;

                sendMessage.accept("Ура, ты нашла меня! " + CONGRATULATION_FACE +
                        " Давай-ка посмотрим который сейчас год... Хмм, 2021..." +
                        " Хозяин поставил на меня блокировку до 2022 года, приходи сразу после боя курантов " + TWELVE_O_CLOCK +
                        " И мы вместе найдём подарок " + PRESENT);
                return;
            }

            sendMessage.accept("Пока рано, надо дождаться Нового Года...");
            return;
        }

        int offset = quiz.getOffset();
        if (offset >= quiz.getQuestionList().size()) {
            return;
        }

        Question question = quiz.getQuestionList().get(offset);
        ProcessInfo processInfo = processQuestion(messageText, question);
        sendMessage.accept(processInfo.getReturnedText());

        if (processInfo.needNextQuestion) {
            Thread.sleep(1000);
            int nextOffset = quiz.getOffset();
            if (nextOffset >= quiz.getQuestionList().size()) {
                return;
            }
            Question nextQuestion = quiz.getQuestionList().get(nextOffset);

            ProcessInfo nextProcessInfo = processQuestion(messageText, nextQuestion);
            sendMessage.accept(nextProcessInfo.getReturnedText());
        }
    }

    private ProcessInfo processQuestion(String messageText, Question question) {
        Answer answer = question.getAnswer();

        if (!question.isAsked()) {
            question.setAsked(true);

            //TODO option questions
            return ProcessInfo.builder()
                    .returnedText(question.getText())
                    .needNextQuestion(false)
                    .build();
        }

        if (answer.getType() == AnswerType.MULTI_OPTION) {
            // Задать вопрос с вариантами ответа
        }

        if (answer.getType() == AnswerType.SINGLE_OPTION) {
            // Задать вопрос с вариантом ответа
        } else if (answer.getType() == AnswerType.TEXT) {
            if (StringUtils.containsIgnoreCase(messageText, answer.getKey())) {
                quiz.setOffset(quiz.getOffset() + 1);

                return ProcessInfo.builder()
                        .returnedText(answer.getRightReply())
                        .needNextQuestion(true)
                        .build();
            }

            return ProcessInfo.builder()
                    .returnedText(answer.getWrongReply())
                    .needNextQuestion(false)
                    .build();
        } else if (answer.getType() == AnswerType.NO_MATTER) {
            quiz.setOffset(quiz.getOffset() + 1);

            return ProcessInfo.builder()
                    .returnedText(answer.getRightReply())
                    .needNextQuestion(true)
                    .build();
        }

        throw new QuizTgBotException("Произошла ошибка при обработке вопроса");
    }

    /**
     * Результат обработки вопроса
     */
    @Getter
    @Setter
    @Builder
    private static class ProcessInfo {

        private final String returnedText;
        private final boolean needNextQuestion;
    }
}
