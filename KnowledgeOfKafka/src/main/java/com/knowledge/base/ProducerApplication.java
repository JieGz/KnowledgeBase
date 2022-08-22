package com.knowledge.base;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;

/**
 * @author jieguangzhi
 * @date 2022-08-22
 */
public class ProducerApplication {

    public static void main(String[] args) {

        final Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.171:9092,192.168.56.172:9092,192.168.56.173:9092");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //指定分区
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName());

        //缓冲区的大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        //批次大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //linger.ms
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //压缩 none, gzip, snappy, lz4, zstd.
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        //设置ack 0,1,-1(default,alias: all)
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        //重度次数, 默认值为Integer.MAX_VALUE
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);

        final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 3; i++) {
            producer.send(new ProducerRecord<>("test", "这是第" + i + "条消息"), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (Objects.isNull(exception)) {
                        final String topic = metadata.topic();
                        final int partition = metadata.partition();
                        final long offset = metadata.offset();
                        System.out.println("topic: " + topic + " partition:" + partition + " offset:" + offset);
                    }
                }
            });
        }

        producer.close();
    }
}
