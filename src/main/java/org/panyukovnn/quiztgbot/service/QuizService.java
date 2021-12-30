package org.panyukovnn.quiztgbot.service;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.model.Question;
import org.panyukovnn.quiztgbot.model.Quiz;
import org.panyukovnn.quiztgbot.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис викторин
 */
@Service
@RequiredArgsConstructor
public class QuizService {

    private final Quiz quiz;
    private final QuizRepository quizRepository;

    public void setQuestionList(List<Question> questionList) {
        quiz.setQuestionList(questionList);
        quizRepository.save(quiz);
    }

    /**
     * Удалить вопрос по порядковому номеру
     *
     * @param ordinal порядковый номер вопроса
     * @return текст удалённого вопроса
     */
    public String deleteQuestionByOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= quiz.getQuestionList().size()) {
            throw new IllegalArgumentException("Неверный порядковый номер вопроса для удаления.");
        }

        String text = quiz.getQuestionList().get(ordinal).getText();

        quiz.getQuestionList().remove(ordinal);
        quizRepository.save(quiz);

        return text;
    }
}
