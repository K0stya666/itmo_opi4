package ru.zevtos.webserver.validation;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

@FacesValidator("validXValidation")
public class ValidXValidator implements ConstraintValidator<ValidX, Double>, Validator<Object> {
    private final Set<Double> validXValues = Set.of(-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0);

    // Bean Validation
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && validXValues.contains(value);
    }

    // JSF Validation
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!(value instanceof Double doubleValue)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Validation Error", "Invalid input: value must be a number."));
        }

        if (!validXValues.contains(doubleValue)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Validation Error", "Invalid input: value must be one of " + validXValues));
        }
    }
}
