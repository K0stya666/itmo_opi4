package ru.zevtos.webserver.beans;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.zevtos.webserver.entities.Result;

import java.util.Set;

@Named("validationBean")
@ApplicationScoped
public class ValidationBean {

    private final ValidatorFactory factory;
    private final Validator validator;

    public ValidationBean() {
        this.factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    /**
     * Метод для валидации объекта Result.
     *
     * @param result объект Result, содержащий входные данные.
     * @throws IllegalArgumentException если данные не проходят валидацию.
     */
    public void validateInput(Result result) {
        // Выполнение валидации объекта Result
        Set<ConstraintViolation<Result>> violations = validator.validate(result);
        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Result> violation : violations) {
                errorMessages.append(violation.getMessage()).append("<br>");
            }
            throw new IllegalArgumentException(errorMessages.toString());
        }
    }

    @PreDestroy
    public void destroy() {
        if (factory != null) {
            factory.close();
        }
    }
}
