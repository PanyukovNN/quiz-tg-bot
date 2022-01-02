package org.panyukovnn.quiztgbot.botcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.panyukovnn.quiztgbot.exception.QuizTgBotException;
import org.panyukovnn.quiztgbot.property.BotProperty;
import org.panyukovnn.quiztgbot.service.QuizBotMessageExecutor;
import org.panyukovnn.quiztgbot.service.QuizBotNonCommandService;
import org.panyukovnn.quiztgbot.service.botcommands.StartCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;

import static org.panyukovnn.quiztgbot.model.Constants.ERROR_WHILE_SEND_MESSAGE;
import static org.panyukovnn.quiztgbot.model.Constants.ERROR_WHILE_SEND_PHOTO;

/**
 * Основной класс бота
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QuizBot extends TelegramLongPollingCommandBot {

    private final BotProperty botProperty;
    private final StartCommand startCommand;
    private final QuizBotNonCommandService nonCommandService;
    private final QuizBotMessageExecutor quizBotMessageExecutor;

    @PostConstruct
    private void postConstruct() {
        register(startCommand);

        // Инкапсулируем логику отправки сообщений в отдельный класс {@link QuizBotMessageExecutor}
        quizBotMessageExecutor.setSendMessageConsumer(this::executeSendMessage);
        quizBotMessageExecutor.setSendPhotoConsumer(this::executeSendPhoto);
    }

    @Override
    public String getBotToken() {
        return botProperty.getBOT_TOKEN();
    }

    @Override
    public String getBotUsername() {
        return botProperty.getBOT_NAME();
    }

    /**
     * Обработка простого сообщения пользователя
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        synchronized (nonCommandService.lock) {
            if (nonCommandService.lock.get()) {
                return;
            }

            // После обычного сообщения игра нанчинается (если еще не начата)
            // После этого обрабатываем только ответы

            nonCommandService.lock.set(true);
            try {
                String messageText = "";
                String chatId = "";

                if (update.hasMessage()) {
                    messageText = update.getMessage().getText();
                    chatId = update.getMessage().getChatId().toString();
                } else if (update.hasCallbackQuery()) {
                    messageText = update.getCallbackQuery().getData();
                    chatId = update.getCallbackQuery().getMessage().getChatId().toString();
                }

                //TODO ограничить userid
                nonCommandService.processMessage(chatId, messageText);
            } finally {
                nonCommandService.lock.set(false);
            }
        }
    }

    /**
     * Отправка простого соообщения пользователю
     *
     * @param sendMessage сообщение
     */
    private void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new QuizTgBotException(ERROR_WHILE_SEND_MESSAGE, e);
        }
    }

    /**
     * Отправка пользователю сообщения с фото
     *
     * @param sendPhoto сообщение с фото
     */
    private void executeSendPhoto(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new QuizTgBotException(ERROR_WHILE_SEND_PHOTO, e);
        }
    }
}
