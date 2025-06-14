package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.ApplicationScoped;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

@ApplicationScoped
public class PointsMonitoring extends NotificationBroadcasterSupport implements PointsMonitoringMBean {
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
        totalPoints++;
    }

    @Override
    public void incrementOutOfBoundsPoints() {
        outOfBoundsPoints++;
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
    }
}