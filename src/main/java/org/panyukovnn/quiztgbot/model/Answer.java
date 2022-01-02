package org.panyukovnn.quiztgbot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.panyukovnn.quiztgbot.model.validator.ValidAnswer;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Ответ
 */
@Getter
@Setter
@ValidAnswer
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer {

    /**
     * Если ответ текстовый, то по ключу отпределяется верный ли он
     * Например если правильный ответ слово "Окно" а ключ "окн", то овет будет считаться корректным
     */
    private String key;

    /**
     * Варианты ответа, если не текстовы тип
     */
    private List<Option> options = new ArrayList<>();

    /**
     * Реплика при правильном ответе
     */
    private String rightReply;

    /**
     * Реплика при ошибочном ответе
     */
    private String wrongReply;

    /**
     * Тип ответа
     * В зависимости от значения будет либо текстовый ответ, либо ответ с вариантами,
     * из каоторых как минимум один должен быть правильный
     */
    @NotNull
    private AnswerType type;
}
