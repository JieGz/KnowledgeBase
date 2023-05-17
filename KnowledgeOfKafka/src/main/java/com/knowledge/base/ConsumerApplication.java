package com.knowledge.base;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author jieguangzhi
 * @date 2022-08-23
 */
public class ConsumerApplication {

    public static void main(String[] args) throws InterruptedException {
        final Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "sl-fs-test001-001.inshopline.com:8124,sl-fs-test001-002.inshopline.com:8124,sl-fs-test001-002.inshopline.com:8124");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        //消费者组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test10");

        //自动提交(默认是5秒自动提交offset),手动提交需要设置成false
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        //提交间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        final KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(properties);

        final ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        consumer.subscribe(topics);
        final Map<TopicPartition, OffsetAndMetadata> allConsumed = new HashMap<>(16);
        while (true) {
            System.out.println("---------");
            final ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofMillis(500L));
            for (ConsumerRecord<byte[], byte[]> record : records) {
                System.out.println("P" + record.partition() + " " + new String(record.value(), StandardCharsets.UTF_8));
                final int partition = record.partition();
                final String topic = record.topic();
                final TopicPartition topicPartition = new TopicPartition(topic, partition);
                final OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(2L);
                allConsumed.put(topicPartition, offsetAndMetadata);
            }
            consumer.commitSync(allConsumed);
            TimeUnit.SECONDS.sleep(5L);
            //手动提交-同步提交
            //consumer.commitSync();
            //手动提交-异步提交
            //consumer.commitAsync();
        }

        //consumer.close();
    }
}
