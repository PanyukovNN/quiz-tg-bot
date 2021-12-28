package org.panyukovnn.quiztgbot.model.validator;

import io.micrometer.core.instrument.util.StringUtils;
import org.panyukovnn.quiztgbot.model.Answer;
import org.panyukovnn.quiztgbot.model.AnswerType;
import org.panyukovnn.quiztgbot.model.Option;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор ответа
 */
public class AnswerValidator implements ConstraintValidator<ValidAnswer, Answer> {

    private String message;

    @Override
    public void initialize(ValidAnswer validAnswer) {
        this.message = validAnswer.message();
    }

    @Override
    public boolean isValid(Answer answer, ConstraintValidatorContext context) {
        if (!isTypeOptionValid(answer)) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        return true;
    }

    private boolean isTypeOptionValid(Answer answer) {
        if (answer.getType() == AnswerType.TEXT
                && StringUtils.isBlank(answer.getKey())) {
            return false;
        }

        long correctAnswerNumber = answer.getOptions().stream().filter(Option::isRight).count();

        if (answer.getType() == AnswerType.SINGLE_OPTION && correctAnswerNumber != 1) {
            return false;
        }

        if (answer.getType() == AnswerType.MULTI_OPTION && correctAnswerNumber <= 1) {
            return false;
        }

        return true;
    }
}
