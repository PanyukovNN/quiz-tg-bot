package org.panyukovnn.quiztgbot.service.questionprocessor;

import org.panyukovnn.quiztgbot.exception.QuizTgBotException;
import org.panyukovnn.quiztgbot.model.AnswerType;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.panyukovnn.quiztgbot.model.AnswerType.SPECIAL_WITH_IMG;

/**
 * Менеджер обработчиков ответов
 */
@Service
public class AnswerProcessorResolver {

    private final Map<AnswerType, AnswerProcessor> type2Processor = new HashMap<>();

    public AnswerProcessorResolver(NoMatterOptionAnswerProcessorImpl noMatterOptionAnswerProcessor,
                                   Set<AnswerProcessor> answerProcessors) {
        answerProcessors.forEach(ap -> type2Processor.put(ap.getType(), ap));

        //TODO убрать
        type2Processor.put(SPECIAL_WITH_IMG, noMatterOptionAnswerProcessor);
    }

    public AnswerProcessor getProcessor(@NotNull AnswerType type) {
        AnswerProcessor answerProcessor = type2Processor.get(type);

        if (answerProcessor == null) {
            throw new QuizTgBotException("Не удалось определить обработчик ответов.");
        }

        return answerProcessor;
    }
}
