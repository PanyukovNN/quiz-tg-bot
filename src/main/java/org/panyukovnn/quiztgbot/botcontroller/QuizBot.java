package org.panyukovnn.quiztgbot.botcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.panyukovnn.quiztgbot.service.QuizBotNonCommandService;
import org.panyukovnn.quiztgbot.service.botcommands.StartCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

import static org.panyukovnn.quiztgbot.model.Constants.BOT_NAME;
import static org.panyukovnn.quiztgbot.model.Constants.BOT_TOKEN;

/**
 * Основной класс бота
 */
@Slf4j
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

        String answer = nonCommandService.processMessage(msg.getText() + "1");

        executeAnswer(chatId, answer);
    }

    /**
     * Отправка ответа
     *
     * @param chatId   id чата
     * @param text     текст ответа
     */
    private void executeAnswer(Long chatId, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }
}
