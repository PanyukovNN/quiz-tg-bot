package org.panyukovnn.quiztgbot.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    /**
     * Сообщения
     */
    public static final String ERROR_OCCURRED_MSG = "Возникла ошибка: ";

    /**
     * Ключи
     */
    public static final String ANSWER_KEY = "answer";

    /**
     * Сообщения об ошибке
     */
    public static final String WRONG_QUESTION_ID_ERROR_MSG = "Неверный id вопроса";
}
