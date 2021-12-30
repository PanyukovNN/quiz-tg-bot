package org.panyukovnn.quiztgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.panyukovnn.quiztgbot.model.Constants.*;

/**
 * Обработка простых сообщений пользователя
 */
@Service
@RequiredArgsConstructor
public class QuizBotNonCommandService {

    private final DateTimeService dateTimeService;

    //TODO remove
    private boolean veryFirstStart = true;

    /**
     * Обработка сообщения пользователя
     *
     * @param text текст сообщения пользователя
     * @return текст ответа
     */
    public String processMessage(String text) {
//        if (!dateTimeService.is2022Year()) {
//            if (veryFirstStart) {
//                veryFirstStart = false;
//
//                return "Ура, ты нашла меня! " + CONGRATULATION_FACE +
//                        " Давай-ка посмотрим который сейчас год... Хмм, 2021..." +
//                        " Хозяин поставил на меня блокировку до 2022 года, приходи сразу после боя курантов " + TWELVE_O_CLOCK +
//                        " И мы вместе найдём подарок " + PRESENT;
//            }
//
//            return "Пока рано, надо дождаться Нового Года...";
//        }

        return ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toString();
    }
}
