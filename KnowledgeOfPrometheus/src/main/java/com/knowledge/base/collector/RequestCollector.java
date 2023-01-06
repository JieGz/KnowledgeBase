package com.knowledge.base.collector;

import io.prometheus.client.Collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jieguangzhi
 * @date 2023-01-06
 */
public class RequestCollector extends Collector {
    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<>();
        final String metricName = "request_count";
        MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(metricName, Arrays.asList("host"), Arrays.asList("127.0.0.1"), 4);
        MetricFamilySamples.Sample sample2 = new MetricFamilySamples.Sample(metricName, Arrays.asList("host", "hostname"), Arrays.asList("127.0.0.1", "127.0.0.1"), 3);

        MetricFamilySamples samples = new MetricFamilySamples(metricName, Type.GAUGE, "请求数", Arrays.asList(sample, sample2));
        mfs.add(samples);
        return mfs;
    }
}
