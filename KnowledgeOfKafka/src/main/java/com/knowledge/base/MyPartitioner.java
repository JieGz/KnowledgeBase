package com.knowledge.base;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author jieguangzhi
 * @date 2022-12-13
 */
public class MyPartitioner implements Partitioner {
    private final Partitioner p = new DefaultPartitioner();

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (key instanceof String) {
            String strPartition = (String) key;
            final int size = cluster.partitionsForTopic(topic).size();
            try {
                final int partition = Integer.parseInt(strPartition);
                int realPartition = partition % size;
                return realPartition;
            } catch (Exception e) {
                System.out.println("丢失partition数据");
            }
        }
        return p.partition(topic, key, keyBytes, value, valueBytes, cluster);
    }

    @Override
    public void close() {
        p.close();
    }

    @Override
    public void configure(Map<String, ?> configs) {
        p.configure(configs);
    }
}
