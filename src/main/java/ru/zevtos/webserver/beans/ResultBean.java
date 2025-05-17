package ru.zevtos.webserver.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.zevtos.webserver.entities.Result;

import java.io.Serializable;

@Named("resultBean")
@ViewScoped
public class ResultBean implements Serializable {

    @Inject
    private ResultListBean resultListBean; // Бин для управления списком результатов

    /**
     * Обработчик попадания точки.
     *
     * @param result объект Result, который нужно обработать.
     */
    public void checkHit(Result result) {
        // Проверка попадания в область
        result.setHit(checkPoint(result));

        // Сохранение результата
        saveResult(result);
    }

    /**
     * Сохраняет результат, добавляя его в ResultListBean.
     *
     * @param result объект Result, который нужно сохранить.
     */
    private void saveResult(Result result) {
        resultListBean.addResult(result);
    }

    /**
     * Проверяет, попадает ли точка в область.
     *
     * @param result объект Result с параметрами для проверки попадания.
     * @return true, если точка попадает в область, иначе false.
     */
    private boolean checkPoint(Result result) {
        double x = result.getX();
        double y = result.getY();
        double r = result.getR();

        // Четверть круга в левом нижнем углу
        if (x <= 0 && y <= 0 && (x * x + y * y <= r * r / 4)) {
            return true;
        }

        // Треугольник в верхнем правом углу
        if (x >= 0 && y >= 0 && y <= r - (x * 2)) {
            return true;
        }

        // Прямоугольник в верхнем левом углу
        return x <= 0 && x >= -(r / 2) && y <= r && y >= 0;
    }

    /**
     * Очищает все результаты.
     */
    public void clearResults() {
        resultListBean.clearResults();
    }

    /**
     * Удаляет результат по идентификатору.
     *
     * @param id идентификатор результата для удаления.
     */
    public void deleteResult(Long id) {
        resultListBean.deleteResult(id);
    }
}
