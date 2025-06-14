package ru.zevtos.webserver.beans;

public interface PointsMonitoringMBean {
    long getTotalPoints();

    long getOutOfBoundsPoints();

    void incrementTotalPoints();

    void incrementOutOfBoundsPoints();

    void resetCounters();
}