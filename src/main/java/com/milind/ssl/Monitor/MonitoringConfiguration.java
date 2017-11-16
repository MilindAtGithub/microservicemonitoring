package com.milind.ssl.Monitor;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.MemoryPoolsExports;
import io.prometheus.client.hotspot.StandardExports;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Milind on 13/11/17.
 */
@Configuration
@ConditionalOnClass(CollectorRegistry.class)
public class MonitoringConfiguration {

    private List<Collector> collectors;

    public void addCollectors (List<Collector> collectors) {
        for (Collector collector : collectors) {
            collector.register();
        }
        this.collectors = collectors;
    }

    public List<Collector> getCollectors() {
        return collectors;
    }

    @Bean
    @ConditionalOnMissingBean
    CollectorRegistry metricRegistry() {
        return CollectorRegistry.defaultRegistry;
    }

    @Bean
    ServletRegistrationBean registerPrometheusExporterServlet(CollectorRegistry metricRegistry) {
        return new ServletRegistrationBean(new MetricsServlet(metricRegistry), "/prometheus");
    }

    @Bean
    public SpringBootMetricsCollector metricsCollector(Collection<PublicMetrics> publicMetrics) {

        List<Collector> collectors = new ArrayList<>();
        collectors.add(new StandardExports());
        collectors.add(new MemoryPoolsExports());
        addCollectors(collectors);
        SpringBootMetricsCollector springBootMetricsCollector = new SpringBootMetricsCollector(
                publicMetrics);
        springBootMetricsCollector.register();
        return springBootMetricsCollector;
    }
}
