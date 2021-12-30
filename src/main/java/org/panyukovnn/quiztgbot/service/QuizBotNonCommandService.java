package org.panyukovnn.quiztgbot.service;

import org.springframework.stereotype.Service;

/**
 * Обработка простых сообщений пользователя
 */
@Service
public class QuizBotNonCommandService {

    /**
     * Обработка сообщения пользователя
     *
     * @param text текст сообщения пользователя
     * @return текст ответа
     */
    public String processMessage(String text) {


        return text;
    }
}
