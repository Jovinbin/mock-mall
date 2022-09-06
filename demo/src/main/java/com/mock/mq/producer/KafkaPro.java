package com.mock.mq.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.*;
import java.util.Properties;

/**
 * @author zhao
 * @date 2022-06-01 11:59
 * @description:
 */
public class KafkaPro {

    public static void main(String[] args) throws InterruptedException {
        String property = System.getProperty("os.name");
        System.out.println("property = " + property);

        String property1 = System.getProperty("user.dir");
        System.out.println("property1 = " + property1);

        // 1. 创建kafka生产者的配置对象
        Properties properties = new Properties();

        // 2. 给kafka配置对象添加配置信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.9.20:9092");
        // 启用幂等性，开启默认ack为-1(all)，解决ack为-1时数据可能会重复消费的问题，但是仅局限于同一分区，无法保证跨分区会话的幂等性
//        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        // 3. leader将等待所有副本同步后应答消息。此配置保障消息不会丢失（只要至少有一个同步的副本）。这是最强壮的可用性保障。等价于acks=-1。
        properties.put(ProducerConfig.ACKS_CONFIG, "all");//持久化机制
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);//batch发送时间  1毫秒发送提交
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);//重试次数
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);//批量发送消息的大小为16KB
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432); //缓存区内存32M

        // key,value序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 3. 创建kafka生产者对象
        Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 4. 调用send方法,发送消息
        for (int i = 0; i < 1; i++) {
            // 同步发送 加上.get()基本不会用上
//            kafkaProducer.send(new ProducerRecord("test01",Integer.toString(i))).get();
            kafkaProducer.send(new ProducerRecord("test_doris01","{\"data\": [{\"id\": 1,\"name\": \"zs\",\"ts\": \"2022-07-18 17:19:09\",\"counts\": 1},{\"id\": 2,\"name\": \"ww\",\"ts\": \"2022-07-19 17:19:09\",\"counts\": 1}]}"));
        }

        // 5. 关闭资源
        kafkaProducer.close();
    }

    /**
     * 文件copy
     * @param res
     * @param des
     * @return
     */
    public static boolean toCopy(String res,String des){
        boolean flag=false;
        Boolean bool1 = res.endsWith(".xxx");
        Boolean bool2 = des.endsWith(".xxxb");
        if(!bool1 && !bool2){
            return false;
        }
        //输入源文件
        File file = new File(res) ;
        FileInputStream fr=null;
        //复制目标文件
        File desFile = new File(des);
        FileOutputStream bw=null;
        try {
            fr = new FileInputStream(file);
            bw = new FileOutputStream(desFile);
            byte[] b = new byte[512];
            while(fr.read(b)!=-1){
                bw.write(b);
            }
            bw.flush();
            flag=true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


}
