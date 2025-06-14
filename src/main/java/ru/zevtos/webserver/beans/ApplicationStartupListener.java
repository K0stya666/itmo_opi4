package ru.zevtos.webserver.beans;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    @Inject
    private MBeanConfig mBeanConfig;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        mBeanConfig.registerMBeans();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        mBeanConfig.unregisterMBeans();
    }
}