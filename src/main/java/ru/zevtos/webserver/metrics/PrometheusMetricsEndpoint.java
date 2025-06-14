package ru.zevtos.webserver.metrics;

import io.prometheus.client.CollectorRegistry;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.prometheus.client.exporter.common.TextFormat;

import java.io.StringWriter;

@Path("/metrics")
public class PrometheusMetricsEndpoint {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response scrape() throws Exception {
        StringWriter writer = new StringWriter();
        // По умолчанию возвращает все зарегистрированные метрики
        TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
        return Response
                .ok(writer.toString(), MediaType.TEXT_PLAIN + "; version=0.0.4; charset=utf-8")
                .build();
    }
}
