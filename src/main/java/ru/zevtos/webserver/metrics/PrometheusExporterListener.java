package ru.zevtos.webserver.metrics;

import io.prometheus.client.hotspot.DefaultExports;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class PrometheusExporterListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Регистрируем стандартные метрики Hotspot (heap, gc, threads)
        DefaultExports.initialize();
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) { }
}
