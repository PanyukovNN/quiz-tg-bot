package org.panyukovnn.quiztgbot.controller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.quiztgbot.model.Quiz;
import org.panyukovnn.quiztgbot.model.request.SetQuestionListRequest;
import org.panyukovnn.quiztgbot.service.QuizService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.panyukovnn.quiztgbot.model.Constants.QUESTION_LIST_SUCCESSFULLY_SET;
import static org.panyukovnn.quiztgbot.model.Constants.QUESTION_SUCCESSFULLY_DELETED;

/**
 * Контроллер вопросов
 */
@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final Quiz quiz;
    private final QuizService quizService;

    /**
     * Вернуть викторину
     *
     * @return единственная викторина
     */
    @GetMapping("/get")
    public Quiz get() {
        return quiz;
    }

    /**
     * Задать вопросы викторины
     *
     * @param request запрос
     * @return сообщение об успешном ответе
     */
    @PostMapping("/set-question-list")
    public String setQuestionList(@RequestBody @Valid SetQuestionListRequest request) {
        quizService.setQuestionList(request.getQuestionList());

        return QUESTION_LIST_SUCCESSFULLY_SET;
    }

    /**
     * Удалить вопрос по порядковому номеру
     *
     * @param ordinal порядковый номер
     * @return сообщение об успешном удалении
     */
    @DeleteMapping("/delete-question")
    public String deleteById(@RequestParam @NotNull Integer ordinal) {
        String removedQuestionText = quizService.deleteQuestionByOrdinal(ordinal);

        return String.format(QUESTION_SUCCESSFULLY_DELETED, removedQuestionText);
    }
}
