package org.panyukovnn.quiztgbot.service.botcommands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Служебная команда /start
 */
@Service
public class StartCommand implements IBotCommand {

    private static final String IDENTIFIER = "start";
    private static final String DESCRIPTION = "Старт";

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
        System.out.println("start");
    }
}
