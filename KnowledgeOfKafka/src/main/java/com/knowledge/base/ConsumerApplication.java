package com.knowledge.base;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author jieguangzhi
 * @date 2022-08-23
 */
public class ConsumerApplication {

    public static void main(String[] args) throws InterruptedException {
        final Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "sl-fs-kpl-kafka-test-0.sldp-kafka.self.yydevops.com:9190,sl-fs-kpl-kafka-test-1.sldp-kafka.self.yydevops.com:9191");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        //消费者组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "jdp-linkhub-test");

        //自动提交(默认是5秒自动提交offset),手动提交需要设置成false
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        //提交间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        properties.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
        String saslJaasConfig = "org.apache.kafka.common.security.scram." +
                "ScramLoginModule required username=\"%s\" password=\"%s\";";
        saslJaasConfig = String.format(saslJaasConfig, "realtime", "realtime");
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);


        final KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(properties);

        final ArrayList<String> topics = new ArrayList<>();
        topics.add("linkhub_sync_upgrade_webslcodetrack");
        consumer.subscribe(topics);
        final Map<TopicPartition, OffsetAndMetadata> allConsumed = new HashMap<>(16);
        while (true) {
            final ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofMillis(500L));
            for (ConsumerRecord<byte[], byte[]> record : records) {
                final int partition = record.partition();
                final String topic = record.topic();
                final String s = new String(record.value());
                if (Objects.nonNull(s) && s.contains("f58005203d3a4b70b11913b6273810f0")) {
                    System.out.println("发现关键数据:" + s);
                }

                final TopicPartition topicPartition = new TopicPartition(topic, partition);
                final OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(2L);
                allConsumed.put(topicPartition, offsetAndMetadata);
            }
            consumer.commitSync(allConsumed);
            TimeUnit.SECONDS.sleep(1L);
        }

        //consumer.close();
    }
}
