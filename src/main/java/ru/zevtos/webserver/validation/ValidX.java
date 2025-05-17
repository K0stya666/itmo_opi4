package ru.zevtos.webserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации значения X.
 * <p>
 * Ограничивает возможные значения X, которые должны быть одними из следующих: -3, -2, -1, 0, 1, 2, 3, 4, 5.
 * Используется для проверки корректности введенного пользователем значения X.
 */
@Documented
@Constraint(validatedBy = ValidXValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidX {
    /**
     * Сообщение об ошибке, которое будет отображено в случае некорректного значения X.
     *
     * @return сообщение об ошибке
     */
    String message() default "Invalid value for x. Must be one of -3, -2, -1, 0, 1, 2, 3, 4, 5.";

    /**
     * Группы валидации, к которым принадлежит данная аннотация.
     *
     * @return массив групп валидации
     */
    Class<?>[] groups() default {};

    /**
     * Дополнительная информация о полезной нагрузке (payload).
     *
     * @return массив полезной нагрузки
     */
    Class<? extends Payload>[] payload() default {};
}
