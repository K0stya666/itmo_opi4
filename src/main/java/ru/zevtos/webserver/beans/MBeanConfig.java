package ru.zevtos.webserver.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@ApplicationScoped
public class MBeanConfig {
    @Inject
    private PointsMonitoring pointsMonitoring;
    @Inject
    private ClickTiming clickTiming;
    private ObjectName pointsName;
    private ObjectName clickName;

    public void registerMBeans() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            pointsName = new ObjectName("ru.zevtos.webserver:type=PointsMonitoring");
            clickName = new ObjectName("ru.zevtos.webserver:type=ClickTiming");

            pointsMonitoring.setObjectName(pointsName);
            mbs.registerMBean(pointsMonitoring, pointsName);

            mbs.registerMBean(clickTiming, clickName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterMBeans() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            if (pointsName != null) {
                mbs.unregisterMBean(pointsName);
            }
            if (clickName != null) {
                mbs.unregisterMBean(clickName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PointsMonitoring getPointsMonitoring() {
        return pointsMonitoring;
    }

    public ClickTiming getClickTiming() {
        return clickTiming;
    }
}