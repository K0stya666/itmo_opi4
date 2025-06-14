package ru.zevtos.webserver.beans;

import io.prometheus.client.Counter;
import jakarta.enterprise.context.ApplicationScoped;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

@ApplicationScoped
public class PointsMonitoring extends NotificationBroadcasterSupport implements PointsMonitoringMBean {

    // Счётчики для total и outOfBounds
    private static final Counter TOTAL_POINTS_COUNTER = Counter.build()
            .name("app_total_points")
            .help("Общее число проверенных точек")
            .register();
    private static final Counter OUT_OF_BOUNDS_COUNTER = Counter.build()
            .name("app_out_of_bounds_points")
            .help("Число точек за пределами области")
            .register();

    private long totalPoints = 0;
    private long outOfBoundsPoints = 0;
    private long sequenceNumber = 0;
    private ObjectName objectName;

    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    @Override
    public long getTotalPoints() {
        return totalPoints;
    }

    @Override
    public long getOutOfBoundsPoints() {
        return outOfBoundsPoints;
    }

    @Override
    public void incrementTotalPoints() {
        System.out.println(">>> incrementTotalPoints()");
        totalPoints++;
        // Увеличиваем Prometheus-счётчик
        TOTAL_POINTS_COUNTER.inc();
    }

    @Override
    public void incrementOutOfBoundsPoints() {
        outOfBoundsPoints++;
        // Увеличиваем Prometheus-счётчик
        OUT_OF_BOUNDS_COUNTER.inc();

        // Оставляем вашу нотификацию через JMX
        Notification notification = new Notification(
                "PointOutOfBounds",
                objectName,
                sequenceNumber++,
                System.currentTimeMillis(),
                "A point was set outside the coordinate plane boundaries"
        );
        sendNotification(notification);
    }

    @Override
    public void resetCounters() {
        totalPoints = 0;
        outOfBoundsPoints = 0;
        // Prometheus client не умеет сбрасывать счётчики в рантайме:
        // они растут только вверх. Для тестов можно сбросить весь реестр:
        // CollectorRegistry.defaultRegistry.clear();
    }
}