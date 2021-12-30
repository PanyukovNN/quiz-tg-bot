package org.panyukovnn.quiztgbot.exception;

/**
 * Базовый класс исключений
 */
public class QuizTgBotException extends RuntimeException {

    /**
     * ctor
     *
     * @param message сообщение об ошибке
     * @param e исключение
     */
    public QuizTgBotException(String message, Exception e) {
        super(message, e);
    }
}
