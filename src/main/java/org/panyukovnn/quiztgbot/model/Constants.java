package org.panyukovnn.quiztgbot.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    /**
     * Сообщения
     */
    public static final String ERROR_OCCURRED_MSG = "Возникла ошибка: ";
    public static final String QUESTION_SUCCESSFULLY_DELETED = "Вопрос \"%s\" успешно удален.";
    public static final String QUESTION_LIST_SUCCESSFULLY_SET = "Список вопросов успешно задан.";

    /**
     * Ключи
     */
    public static final String ANSWER_KEY = "answer";

    /**
     * Сообщения об ошибке
     */
    public static final String WRONG_QUESTION_ID_ERROR_MSG = "Неверный id вопроса";

    /**
     * Смайлики
     */
    public static final String CONGRATULATION_FACE = "\uD83E\uDD73";
    public static final String CHRISTMAS_TREE = "\uD83C\uDF84";
    public static final String SLEEPING_FACE = "\uD83D\uDE34";
    public static final String PRESENT = "\uD83C\uDF81";
    public static final String TWELVE_O_CLOCK = "\uD83D\uDD5B";

}
