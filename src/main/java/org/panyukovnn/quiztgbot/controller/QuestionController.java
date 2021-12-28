package org.panyukovnn.quiztgbot.controller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.model.Question;
import org.panyukovnn.quiztgbot.service.QuestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
