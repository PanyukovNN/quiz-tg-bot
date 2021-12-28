package org.panyukovnn.quiztgbot.botcontroller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.service.QuizBotNonCommandService;
import org.panyukovnn.quiztgbot.service.botcommands.StartCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

import static org.panyukovnn.quiztgbot.model.Constants.BOT_NAME;
import static org.panyukovnn.quiztgbot.model.Constants.BOT_TOKEN;

/**
 * Основной класс бота
 */
@Service
@RequiredArgsConstructor
public class QuizBot extends TelegramLongPollingCommandBot {

    private final StartCommand startCommand;
    private final QuizBotNonCommandService nonCommandService;

    @PostConstruct
    private void postConstruct() {
        register(startCommand);
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Обработка простого сообщения бользователя
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);

        String answer = nonCommandService.processMessage(chatId, userName, msg.getText());

        executeAnswer(chatId, userName, answer);
    }

    /**
     * Формирование имени пользователя
     *
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     *
     * @param chatId   id чата
     * @param userName имя пользователя
     * @param text     текст ответа
     */
    private void executeAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName
        }
    }
}
