package com.knowledge.base;

import com.knowledge.base.collector.RequestTimeGauge;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author jieguangzhi
 * @date 2023-01-06
 */
public class PrometheusApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("666666");
        RequestTimeGauge.processRequest();

        //DefaultExports.initialize();

        final HTTPServer server = new HTTPServer(1234);
        //new RequestCollector().register();

        while (true) {
            TimeUnit.SECONDS.sleep(1L);
            RequestTimeGauge.processRequest();
            RequestTimeGauge.processPostRequest();
        }
    }
}
