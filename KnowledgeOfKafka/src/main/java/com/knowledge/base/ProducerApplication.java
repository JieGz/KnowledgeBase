package com.knowledge.base;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author jieguangzhi
 * @date 2022-08-22
 */
public class ProducerApplication {

    public static void main(String[] args) throws InterruptedException {

        final Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "sl-fs-test001-001.inshopline.com:8124,sl-fs-test001-002.inshopline.com:8124,sl-fs-test001-002.inshopline.com:8124");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        //指定分区
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CdcPartitioner.class.getName());

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

        final KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(properties);
        for (int i = 60; i < 90; i++) {
            String content = "这是第" + i + "条消息";
            final ProducerRecord<byte[], byte[]> message;
            message = new ProducerRecord<>("test", String.valueOf(i).getBytes(StandardCharsets.UTF_8), content.getBytes(StandardCharsets.UTF_8));
            producer.send(message, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (Objects.isNull(exception)) {
                        final String topic = metadata.topic();
                        final int partition = metadata.partition();
                        final long offset = metadata.offset();
                        System.out.println("topic: " + topic + " partition:" + partition + " offset:" + offset);
                    } else {
                        System.out.println("-------------------------->2" + exception.getMessage());
                    }
                }
            });
            TimeUnit.SECONDS.sleep(5L);
        }

        producer.close();
    }
}
