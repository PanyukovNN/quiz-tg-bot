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
     * @param chatId
     * @param userName
     * @param text
     * @return
     */
    public String processMessage(Long chatId, String userName, String text) {
        return null;
    }
}
