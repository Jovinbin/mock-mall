package com.mock.doris.param;

import lombok.Data;

/**
 * @author zhao
 * @since 2022-08-09 9:49
 */
@Data
public class DorisKafkaParam {

    /**
     * Kafka集群
     */
    private String kafkaBrokerList;

    /**
     * 消费主题
     */
    private String kafkaTopic;

    /**
     * 消费组
     */
    private String consumerGroupId;

    private String kafkaDefaultOffset = "\"OFFSET_BEGINNING\"";

    private String enableAutoCommit = "\"false\"";

    public void setKafkaBrokerList(String kafkaBrokerList) {
        this.kafkaBrokerList = "\"" + kafkaBrokerList + "\"";
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = "\"" + kafkaTopic + "\"";
    }

    public void setConsumerGroupId(String consumerGroupId) {
        this.consumerGroupId = "\"" + consumerGroupId + "\"";
    }

    public void setKafkaDefaultOffset(String kafkaDefaultOffset) {
        this.kafkaDefaultOffset = "\"" + kafkaDefaultOffset + "\"";
    }

    public void setEnableAutoCommit(String enableAutoCommit) {
        this.enableAutoCommit = "\"" + enableAutoCommit + "\"";
    }
}
