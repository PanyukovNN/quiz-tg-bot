package org.panyukovnn.quiztgbot.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.Optional;

/**
 * Результат обработки сообщения пользователя
 */
@Getter
@Setter
@Builder
public class MessageProcessInfo {

    private final Optional<SendPhoto> sendPhoto;
    private final Optional<SendMessage> sendMessage;
}
