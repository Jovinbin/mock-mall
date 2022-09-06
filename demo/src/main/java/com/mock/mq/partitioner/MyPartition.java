package com.mock.mq.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区器
 * @author zhao
 * @since 2022-07-07 17:11
 */
public class MyPartition implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value,
                         byte[] valueBytes, Cluster cluster) {
//        Integer integer = cluster.partitionCountForTopic(topic);
//        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(topic);
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
