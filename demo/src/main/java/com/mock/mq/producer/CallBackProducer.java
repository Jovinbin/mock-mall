package com.mock.mq.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 带回调函数的生产者
 * @author zhao
 * @since 2022-07-07 16:40
 */
public class CallBackProducer {

    public static void main(String[] args) throws InterruptedException {
        // 1、创建配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.9.20:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // 2、创建生产者对象
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);

        // 3、发送数据
        for (int i = 0; i < 10; i++) {

            producer.send(new ProducerRecord("test02",2,"", "zzz--" + i), (metadata, exception) -> {
                if (exception == null){
                    System.out.println(metadata.partition() + "--" + metadata.offset());
                }
            });
            Thread.sleep(1);
        }

        // 4、关闭资源
        producer.close();
    }

}
