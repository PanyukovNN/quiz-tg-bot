package org.panyukovnn.quiztgbot.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации сущности ответа
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Constraint(validatedBy = AnswerValidator.class)
public @interface ValidAnswer {

    String message() default "Невалидный ответ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
