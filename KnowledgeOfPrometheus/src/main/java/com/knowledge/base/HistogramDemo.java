package com.knowledge.base;


import io.prometheus.metrics.core.metrics.Histogram;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.model.snapshots.Unit;

import java.util.Random;

/**
 * 直方图Demo
 *
 * @author jieguangzhi
 * @date 2023-11-17
 */
public class HistogramDemo {
    public static void main(String[] args) throws Exception {

        Histogram histogram = Histogram.builder()
                .name("request_latency_seconds")
                .help("request latency in seconds")
                .unit(Unit.SECONDS)
                .labelNames("path", "status")
                .register();

        HTTPServer server = HTTPServer.builder()
                .port(1234)
                .buildAndStart();

        System.out.println("HTTPServer listening on port http://localhost:" + server.getPort() + "/metrics");

        Random random = new Random(0);


        while (true) {
            double duration = Math.abs(random.nextGaussian() / 10.0 + 0.2);
            String status = random.nextInt(100) < 20 ? "500" : "200";
            histogram.labelValues("/", status).observe(duration);
            Thread.sleep(1000);
        }
    }
}
