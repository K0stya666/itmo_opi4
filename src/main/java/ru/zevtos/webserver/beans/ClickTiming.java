package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClickTiming implements ClickTimingMBean {
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
        long currentTime = System.currentTimeMillis();
        if (lastClickTime != 0) {
            totalIntervals += (currentTime - lastClickTime);
        }
        lastClickTime = currentTime;
        clickCount++;
    }

    @Override
    public void resetTiming() {
        lastClickTime = 0;
        totalIntervals = 0;
        clickCount = 0;
    }
}