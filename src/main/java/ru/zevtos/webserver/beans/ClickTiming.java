package ru.zevtos.webserver.beans;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

@ApplicationScoped
public class ClickTiming implements ClickTimingMBean {
    private static final Logger LOGGER = Logger.getLogger(ClickTiming.class.getName());

    // Счётчик общего числа кликов
    private static final Counter CLICK_COUNTER = Counter.build()
            .name("app_clicks_total")
            .help("Общее число кликов")
            .register();

    // Гистограмма интервалов между кликами (в миллисекундах)
    private static final Histogram INTERVAL_HISTOGRAM = Histogram.build()
            .name("app_click_intervals_ms")
            .help("Гистограмма интервалов между кликами (мс)")
            .register();

    // Gauge для текущего среднего интервала (обновляется при каждом клике)
    private static final Gauge AVERAGE_INTERVAL_GAUGE = Gauge.build()
            .name("app_average_click_interval_ms")
            .help("Текущий средний интервал между кликами (мс)")
            .register();

    private long lastClickTime = 0;
    private double totalIntervals = 0;
    private int clickCount = 0;

    @Override
    public double getAverageClickInterval() {
        if (clickCount <= 1) {
            return 0.0;
        }
        return totalIntervals / (clickCount - 1);
    }

    @Override
    public void recordClick() {
        LOGGER.info(">>> incrementTotalPoints()");
        long currentTime = System.currentTimeMillis();
        if (lastClickTime != 0) {
            long interval = currentTime - lastClickTime;
            totalIntervals += interval;
            // Добавляем интервал в гистограмму
            INTERVAL_HISTOGRAM.observe(interval);
        }
        lastClickTime = currentTime;
        clickCount++;

        // Увеличиваем счётчик кликов
        CLICK_COUNTER.inc();

        // Обновляем gauge среднего интервала
        double avg = getAverageClickInterval();
        AVERAGE_INTERVAL_GAUGE.set(avg);
    }

    @Override
    public void resetTiming() {
        lastClickTime = 0;
        totalIntervals = 0;
        clickCount = 0;
        // Сбрасываем метрики
        // (Prometheus client не поддерживает «удаление» метрик, но можно обнулять gauge)
        AVERAGE_INTERVAL_GAUGE.set(0);
        // Для гистограммы и счётчика придётся перезапускать приложение,
        // или использовать CollectorRegistry.clear() в тестах.
    }
}