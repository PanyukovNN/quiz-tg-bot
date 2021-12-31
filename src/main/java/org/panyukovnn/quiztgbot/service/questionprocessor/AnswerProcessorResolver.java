package org.panyukovnn.quiztgbot.service.questionprocessor;

import org.panyukovnn.quiztgbot.exception.QuizTgBotException;
import org.panyukovnn.quiztgbot.model.AnswerType;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Менеджер обработчиков ответов
 */
@Service
public class AnswerProcessorResolver {

    private final Map<AnswerType, AnswerProcessor> type2Processor = new HashMap<>();

    public AnswerProcessorResolver(Set<AnswerProcessor> answerProcessors) {
        answerProcessors.forEach(ap -> type2Processor.put(ap.getType(), ap));
    }

    public AnswerProcessor getProcessor(@NotNull AnswerType type) {
        AnswerProcessor answerProcessor = type2Processor.get(type);

        if (answerProcessor == null) {
            throw new QuizTgBotException("Не удалось определить обработчик ответов.");
        }

        return answerProcessor;
    }
}
