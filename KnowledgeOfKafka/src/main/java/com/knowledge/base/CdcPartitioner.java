package com.knowledge.base;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Cdc同步数据分区策略.
 *
 * @author jieguangzhi
 * @date 2022-12-13
 */
public class CdcPartitioner implements Partitioner {
    private final Partitioner partitioner = new DefaultPartitioner();

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
                         Cluster cluster) {

        if (ArrayUtil.isNotEmpty(keyBytes)) {
            String strPartition = new String(keyBytes);
            if (NumberUtil.isNumber(strPartition)) {
                final int size = cluster.partitionsForTopic(topic).size();
                final int partition = Integer.parseInt(strPartition);
                return partition % size;
            } else {
                System.out.println("CdcPartition分区策略失效,原因:丢失partition信息,异常信息: key is not a number!"
                        + ",key:" + key);
            }
        }else {
            System.out.println("666666666");
        }
        return partitioner.partition(topic, key, keyBytes, value, valueBytes, cluster);
    }

    @Override
    public void close() {
        partitioner.close();
    }

    @Override
    public void configure(Map<String, ?> configs) {
        partitioner.configure(configs);
    }
}
