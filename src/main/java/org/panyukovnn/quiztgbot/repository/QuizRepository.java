package org.panyukovnn.quiztgbot.repository;

import org.panyukovnn.quiztgbot.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий викторин
 */
@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {
}
