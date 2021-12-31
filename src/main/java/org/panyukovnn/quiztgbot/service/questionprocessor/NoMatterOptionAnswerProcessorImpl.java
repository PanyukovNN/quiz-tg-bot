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

//    private ProcessInfo processQuestion(String messageText, Question question) {
//
//
//        if (answer.getType() == AnswerType.TEXT) {
//            if (StringUtils.containsIgnoreCase(messageText, answer.getKey())) {
//                quiz.setOffset(quiz.getOffset() + 1);
//
//                return ProcessInfo.builder()
//                        .returnedText(answer.getRightReply())
//                        .needNextQuestion(true)
//                        .build();
//            }
//
//            return ProcessInfo.builder()
//                    .returnedText(answer.getWrongReply())
//                    .needNextQuestion(false)
//                    .build();
//        } else if (answer.getType() == AnswerType.NO_MATTER_TEXT) {
//            quiz.setOffset(quiz.getOffset() + 1);
//
//            return ProcessInfo.builder()
//                    .returnedText(answer.getRightReply())
//                    .needNextQuestion(true)
//                    .build();
//        }
//
//        throw new QuizTgBotException("Произошла ошибка при обработке вопроса");
//    }
}
