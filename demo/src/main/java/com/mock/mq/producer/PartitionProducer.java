package com.mock.mq.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 自定义分区测试
 * @author zhao
 * @since 2022-07-07 17:25
 */
public class PartitionProducer {

    public static void main(String[] args) throws InterruptedException {

        // 1、创建配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.9.20:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        // 添加分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.z.cn.kafka.partitioner.MyPartition");

        // 2、创建生产者对象
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);

        // 4. 调用send方法,发送消息
        for (int i = 0; i < 1; i++) {

            producer.send(new ProducerRecord("test02", "zhh--" + i), (metadata, exception) -> {
                if (exception == null){
                    System.out.println(metadata.partition() + "--" + metadata.offset());
                }
            });
        }

        // 5. 关闭资源
        producer.close();

    }

}
