package com.zwkj.ceng.consumer;

import java.util.List;
import java.util.Map;

import com.zwkj.ceng.entity.ThirdpartyShippingEntity;
import com.zwkj.ceng.mapper.ThirdpartyShippingMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class DbConsumer extends BaseConsumer<String, String> {

    ThirdpartyShippingMapper thirdpartyShippingMapper;

    public DbConsumer(List<String> topics, String groupId, List<TopicPartition> targetPartitions, ThirdpartyShippingMapper thirdpartyShippingMapper) {
        super(topics, groupId, targetPartitions);
        this.thirdpartyShippingMapper = thirdpartyShippingMapper;
    }

    @Override
    protected void doCall(ConsumerRecords<String, String> records,
                          Map<TopicPartition, Long> partitionUncommitOffersetMap) {
        for (ConsumerRecord<String, String> record : records) {
            try {
                Thread.sleep(500);
                ThirdpartyShippingEntity thirdpartyShippingEntity = new ThirdpartyShippingEntity();
                thirdpartyShippingEntity.setShippingCodeLast(record.value());
                thirdpartyShippingMapper.insert(thirdpartyShippingEntity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("record . topic {" + record.topic() + "}  ; partition  {" + record.partition()
                    + "} ; offset {" + record.offset() + "}   " + " record.key " + record.key() + " record.value " + record.value());
            partitionUncommitOffersetMap.put(new TopicPartition(record.topic(), record.partition()), record.offset());
        }
    }

}
