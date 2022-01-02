package org.panyukovnn.quiztgbot.service;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.property.BotProperty;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class DateTimeService {

    private final BotProperty botProperty;

    public boolean is2022Year() {
        if (!botProperty.isTimeLimitEnabled()) {
            return true;
        }

        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));

        return dateTime.getYear() == 2022;
    }
}
