package ru.zevtos.webserver.metrics;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.DefaultExports;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Enumeration;
import java.util.logging.Logger;

@WebListener
public class MetricsBootstrapListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(MetricsBootstrapListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info(">>> MetricsBootstrapListener initialized");

        // Регистрируем стандартные JVM-метрики (heap, GC и т.д.)
        DefaultExports.initialize();

        // «Принудительно» загружаем ваш класс с метриками
        try {
            Class.forName("ru.zevtos.webserver.beans.ClickTiming");

            logger.info("=== Registered metrics at startup ===");
            Enumeration<Collector.MetricFamilySamples> mfs = CollectorRegistry.defaultRegistry.metricFamilySamples();
            while (mfs.hasMoreElements()) {
                Collector.MetricFamilySamples sample = mfs.nextElement();
                System.out.println("  • " + sample.name);
            }
            logger.info("=== End of list ===");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось загрузить ClickTiming", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }
}
