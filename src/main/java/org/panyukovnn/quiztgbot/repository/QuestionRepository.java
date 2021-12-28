package org.panyukovnn.quiztgbot.repository;

import org.panyukovnn.quiztgbot.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий вопросов
 */
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
}
