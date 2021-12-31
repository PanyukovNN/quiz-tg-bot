package org.panyukovnn.quiztgbot.service.questionprocessor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Результат обработки вопроса
 */
@Getter
@Setter
@Builder
public class AnswerProcessInfo {

    private final String textToReturn;
    private final boolean needNextQuestion;
}
