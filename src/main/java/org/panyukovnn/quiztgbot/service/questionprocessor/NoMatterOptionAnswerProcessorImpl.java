package org.panyukovnn.quiztgbot.service.questionprocessor;

import org.panyukovnn.quiztgbot.model.Answer;
import org.panyukovnn.quiztgbot.model.AnswerType;
import org.springframework.stereotype.Service;

@Service
public class NoMatterOptionAnswerProcessorImpl implements AnswerProcessor {

    @Override
    public AnswerProcessInfo process(String messageText, Answer answer) {
        return AnswerProcessInfo.builder()
                .textToReturn(answer.getRightReply())
                .needNextQuestion(true)
                .build();
    }

    @Override
    public AnswerType getType() {
        return AnswerType.NO_MATTER_OPTION;
    }
}
