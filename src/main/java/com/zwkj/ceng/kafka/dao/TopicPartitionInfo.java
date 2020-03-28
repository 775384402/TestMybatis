package com.zwkj.ceng.kafka.dao;

import lombok.Getter;
import org.apache.kafka.common.TopicPartition;
import org.springframework.util.Assert;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Created by mark on 2017/11/29.
 */
@Getter
public class TopicPartitionInfo {

    private int partition;
    private String topic;
    private Long startOffset;
    private Long currentOffset;
    private Date startDate;
    private Date commitDate;
    private String clientId;

    public TopicPartitionInfo() {
    }

    public TopicPartitionInfo(Long startOffset, TopicPartition topicPartition, String clientId) {
        this(startOffset, null, topicPartition, clientId);
    }

    public TopicPartitionInfo(Long startOffset, Long currentOffset, TopicPartition topicPartition, String clientId) {
        Assert.notNull(topicPartition, "topicPartition can not be null.");
        this.startOffset = startOffset;
        this.currentOffset = currentOffset;
        this.partition = topicPartition.partition();
        this.topic = topicPartition.topic();
        this.startDate = new Date();
        this.clientId = clientId;
    }

    public void setCurrentOffset(Long currentOffset) {
        this.currentOffset = currentOffset;
        this.commitDate = new Date();
    }

    @XmlTransient
    public Long getOffset() {
        return currentOffset != null ? currentOffset : startOffset;
    }
}
