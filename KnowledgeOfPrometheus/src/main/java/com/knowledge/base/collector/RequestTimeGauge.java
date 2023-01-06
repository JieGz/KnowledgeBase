package com.knowledge.base.collector;

import io.prometheus.client.Gauge;

/**
 * @author jieguangzhi
 * @date 2023-01-06
 */
public class RequestTimeGauge {

    public static final Gauge REQUEST_TIME_GAUGE = Gauge.build()
            .name("request_time")
            .labelNames("requestType")
            .help("请求时间")
            .register();

    public static void processRequest() {
        REQUEST_TIME_GAUGE.labels("get").inc();
    }

    public static void processPostRequest() {
        REQUEST_TIME_GAUGE.labels("post").inc();
    }
}
