package com.mock.mq.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author zhao
 * @since 2022-07-04 11:02
 */
public class KafkaCon {

    public static void main(String[] args) {
        // 这是个【自动提交偏移量】的简单的kafka消费者API。
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");
        // 设置enable.auto.commit,偏移量由auto.commit.interval.ms控制自动提交的频率。
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "5000");
        // 从最早的消息开始读取
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test03"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
            System.out.println("records = " + records);
            System.out.println("records.count() = " + records.count());
            for (ConsumerRecord<String, String> record : records)
//                System.out.println("===============");
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }

}
