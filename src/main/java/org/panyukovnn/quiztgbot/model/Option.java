package org.panyukovnn.quiztgbot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Вариант ответа
 */
@Getter
@Setter
@NoArgsConstructor
public class Option {

    /**
     * Текст
     */
    private String text;

    /**
     * Является ли вариант правильным
     */
    private boolean isRight;
}
