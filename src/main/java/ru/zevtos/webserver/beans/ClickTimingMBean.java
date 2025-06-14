package ru.zevtos.webserver.beans;

public interface ClickTimingMBean {
    double getAverageClickInterval();

    void recordClick();

    void resetTiming();
}