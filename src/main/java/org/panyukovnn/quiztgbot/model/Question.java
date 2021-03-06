package org.panyukovnn.quiztgbot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Вопрос
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "question")
public class Question {

    /**
     * Текст
     */
    @NotBlank
    private String text;

    /**
     * Ответ
     */
    @Valid
    @NotNull
    private Answer answer;

    /**
     * Задан ли вопрос
     */
    private boolean asked;
}
