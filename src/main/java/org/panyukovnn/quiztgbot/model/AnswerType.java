package org.panyukovnn.quiztgbot.model;

/**
 * Тип ответа
 */
public enum AnswerType {

    /**
     * Без разницы какой ответ (всегда правильный)
     */
    NO_MATTER,

    /**
     * Немедленно задать следующий вопрос
     */
    NEXT_QUESTION,

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
