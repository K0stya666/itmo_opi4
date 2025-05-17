package ru.zevtos.webserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации значения радиуса R.
 * Разрешенные значения: 1, 2, 3, 4, 5.
 */
@Documented
@Constraint(validatedBy = ValidRValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidR {
    /**
     * Сообщение об ошибке.
     */
    String message() default "Invalid value for R. Must be one of 1, 2, 3, 4, 5.";

    /**
     * Группы валидации.
     */
    Class<?>[] groups() default {};

    /**
     * Полезная нагрузка.
     */
    Class<? extends Payload>[] payload() default {};
}
