package com.knowledge.base;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author jieguangzhi
 * @date 2022-08-23
 */
public class ConsumerApplication {

    public static void main(String[] args) {
        final Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.171:9092,192.168.56.172:9092,192.168.56.173:9092");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //消费者组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        //设置分区分配策略
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RangeAssignor");

        //自动提交(默认是5秒自动提交offset),手动提交需要设置成5
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //提交间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);


        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        final ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        consumer.subscribe(topics);

        //1. 指定位置进行消费
        Set<TopicPartition> assignment = consumer.assignment();

        //2. 需要保证分区分配方案已经制定完毕后,才能指定消费的offset
        while (assignment.size() == 0) {
            consumer.poll(Duration.ofMillis(100));
            assignment = consumer.assignment();
        }
        //指定时间消费offset
        HashMap<TopicPartition, Long> topicPartitionLongHashMap = new HashMap<>();

        for (TopicPartition topicPartition : assignment) {
            //1天前的
            topicPartitionLongHashMap.put(topicPartition, System.currentTimeMillis() - 1 * 24 * 3600 * 100);
        }

        final Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = consumer.offsetsForTimes(topicPartitionLongHashMap);

        //3. 指定消费的offset
        for (TopicPartition partition : assignment) {

            consumer.seek(partition, topicPartitionOffsetAndTimestampMap.get(partition).offset());
        }


        while (true) {
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200L));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }

            //手动提交
            //consumer.commitSync();
            //consumer.commitAsync();
        }

        //consumer.close();
    }
}
