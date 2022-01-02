package org.panyukovnn.quiztgbot.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.panyukovnn.quiztgbot.model.Question;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Запрос на установку списка вопросов для викторины
 */
@Getter
@Setter
@NoArgsConstructor
public class SetQuestionListRequest {

    @Valid
    @NotNull
    private List<Question> questionList;
}
