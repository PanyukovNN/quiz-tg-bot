package org.panyukovnn.quiztgbot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность викторины
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "quiz")
public class Quiz {

    private String id;

    /**
     * Список вопросов
     */
    private List<Question> questionList = new ArrayList<>();

    /**
     * Сдвиг, обозначающий на каком вопросе остановились
     */
    private int offset;

    /**
     * Окончена ли викторина
     */
    private boolean finished;
}
