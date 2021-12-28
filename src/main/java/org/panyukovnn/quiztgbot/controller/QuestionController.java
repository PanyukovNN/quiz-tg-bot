package org.panyukovnn.quiztgbot.controller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.model.Question;
import org.panyukovnn.quiztgbot.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.panyukovnn.quiztgbot.model.Constants.QUESTION_SUCCESSFULLY_DELETED;

/**
 * Контроллер вопросов
 */
@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * Сохранить вопрос
     *
     * @param question сущность вопроса
     * @return сохраненный вопрос
     */
    @PostMapping("/save")
    public Question save(@RequestBody @Valid Question question) {
        return questionService.save(question);
    }

    /**
     * Найти все вопросы
     *
     * @return список всех вопросов
     */
    @GetMapping("/find-all")
    public List<Question> findAll() {
        return questionService.findAll();
    }

    /**
     * Удалить по идентификатору
     *
     * @param id идентификатор
     * @return сообщение об успешном удалении
     */
    @DeleteMapping("/delete-by-id")
    public String deleteById(@RequestParam @NotBlank String id) {
        questionService.deleteById(id);

        return String.format(QUESTION_SUCCESSFULLY_DELETED, id);
    }
}
