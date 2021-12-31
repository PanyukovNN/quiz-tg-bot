package org.panyukovnn.quiztgbot.service.questionprocessor;

import org.panyukovnn.quiztgbot.model.Answer;
import org.panyukovnn.quiztgbot.model.AnswerType;

/**
 * Обработчик ответов
 */
public interface AnswerProcessor {

    /**
     * Обработать вопрос
     *
     * @param messageText сообщение пользователя
     * @param answer ответ
     * @return результат обработки вопроса
     */
    AnswerProcessInfo process(String messageText, Answer answer);

    /**
     * @return тип ответа
     */
    AnswerType getType();
}
