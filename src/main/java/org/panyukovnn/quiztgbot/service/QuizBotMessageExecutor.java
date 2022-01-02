package org.panyukovnn.quiztgbot.service;

import lombok.Setter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.function.Consumer;

@Service
public class QuizBotMessageExecutor {

    @Setter
    private Consumer<SendMessage> sendMessageConsumer;
    @Setter
    private Consumer<SendPhoto> sendPhotoConsumer;

    public void executeSendMessage(SendMessage sendMessage) {

    }

    public void executeSendPhoto(SendPhoto sendPhoto) {

    }
}
