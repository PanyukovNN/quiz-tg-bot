package org.panyukovnn.quiztgbot.service;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.model.Question;
import org.panyukovnn.quiztgbot.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.panyukovnn.quiztgbot.model.Constants.WRONG_QUESTION_ID_ERROR_MSG;

/**
 * Сервисо вопросов
 */
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * Сохранить вопрос
     *
     * @param templateQuestion частично заполненная сущность вопроса
     * @return сохоранненный вопрос
     */
    public Question save(Question templateQuestion) {
        boolean notBlankId = StringUtils.isNotBlank(templateQuestion.getId());
        boolean wrongQuestionId = notBlankId
                && !questionRepository.existsById(templateQuestion.getId());

        if (wrongQuestionId) {
            throw new IllegalArgumentException(WRONG_QUESTION_ID_ERROR_MSG);
        }

        return questionRepository.save(templateQuestion);
    }

    /**
     * Найти все вопросы
     *
     * @return список всех ответов
     */
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    /**
     * Удалить по id
     *
     * @param id идентификатор
     */
    public void deleteById(String id) {
        questionRepository.deleteById(id);
    }
}
