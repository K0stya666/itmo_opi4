package ru.zevtos.webserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zevtos.webserver.validation.ValidR;
import ru.zevtos.webserver.validation.ValidX;

import java.beans.JavaBean;
import java.io.Serializable;

/**
 * Result - класс, представляющий результат проверки попадания точки в область.
 * <p>
 * Содержит данные о координатах точки (x, y), радиусе (r), идентификаторе результата,
 * а также информации о попадании точки в область.
 */
@Entity
@Table(name = "results")
@JavaBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result implements Serializable {

    /**
     * Уникальный идентификатор результата (если сохраняется в базу данных).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * /**
     * Координата X точки, проверяемая на попадание в область.
     * Значение должно удовлетворять валидации, заданной аннотацией @ValidX.
     */
    @ValidX
    private double x;

    /**
     * Координата Y точки, проверяемая на попадание в область.
     * Допустимое значение должно находиться в диапазоне от -3 до 5.
     */
    @Min(-3)
    @Max(5)
    private double y;

    /**
     * Радиус R, определяющий размер области проверки.
     * Значение должно удовлетворять валидации, заданной аннотацией @ValidR.
     */
    @ValidR
    private double r;

    /**
     * Флаг, показывающий, попала ли точка в заданную область.
     */
    private boolean hit;
}
