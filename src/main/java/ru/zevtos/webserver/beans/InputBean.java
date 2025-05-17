package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import ru.zevtos.webserver.entities.Result;

import java.io.Serializable;

@Named("inputBean")
@RequestScoped
public class InputBean implements Serializable {

    @Getter
    @Setter
    private Result result = new Result(); // Используем объект Result для хранения данных

    @Inject
    private ValidationBean validationBean;

    @Inject
    private ResultBean resultBean;

    /**
     * Метод для обработки ввода пользователя.
     */
    public void processInput() {
        try {
            // Валидация входных данных
            validationBean.validateInput(result);

            // Выполнение проверки попадания точки
            resultBean.checkHit(result);

            // Текущее состояние Result сохраняется
        } catch (IllegalArgumentException e) {
            // Обработка ошибок валидации
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка валидации", e.getMessage()));
        }
    }
}
