package org.panyukovnn.quiztgbot.model;

/**
 * Тип ответа
 */
public enum AnswerType {

    /**
     * Без разницы какой текстовый ответ
     */
    NO_MATTER_TEXT,

    SPECIAL_WITH_IMG,

    /**
     * Без разницы какой ответ из предложенных вариантов
     */
    NO_MATTER_OPTION,

    /**
     * Текстовый
     */
    TEXT,

    /**
     * Варианты ответа с одним правильным
     */
    SINGLE_OPTION,

    /**
     * Варианты ответа с несколькими правильными
     */
    MULTI_OPTION
}
