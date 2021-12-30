package org.panyukovnn.quiztgbot.config;

import org.panyukovnn.quiztgbot.model.Quiz;
import org.panyukovnn.quiztgbot.repository.QuizRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Конфигурация викторин
 */
@Configuration
public class QuizConfig {

    /**
     * Бин для создания единственной сущности викторины
     *
     * @param quizRepository репозиторий викторин
     * @return викторина
     */
    @Bean
    public Quiz quiz(QuizRepository quizRepository) {
        List<Quiz> allQuiz = quizRepository.findAll();

        if (allQuiz.isEmpty()) {
            return quizRepository.save(new Quiz());
        }

        Quiz quiz = allQuiz.get(0);

        if (quiz.getOffset() != 0) {
            quiz.setOffset(0);
            quizRepository.save(quiz);
        }

        return quiz;
    }
}
