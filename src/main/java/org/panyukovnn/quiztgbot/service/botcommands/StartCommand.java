package org.panyukovnn.quiztgbot.service.botcommands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.panyukovnn.quiztgbot.service.QuizBotNonCommandService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Служебная команда /start
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StartCommand implements IBotCommand {

    private static final String IDENTIFIER = "start";
    private static final String DESCRIPTION = "Старт";

    private final QuizBotNonCommandService nonCommandService;

    @Override
    public String getCommandIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        try {
            SendMessage sendMessage = nonCommandService.processMessage(message);
            absSender.execute(sendMessage);
        } catch (InterruptedException | TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }
}
