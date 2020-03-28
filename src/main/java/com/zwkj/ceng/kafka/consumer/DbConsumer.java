package com.zwkj.ceng.kafka.consumer;

import com.zwkj.ceng.mybatis.entity.ThirdpartyShippingEntity;
import com.zwkj.ceng.mybatis.mapper.ThirdpartyShippingMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Map;


public class DbConsumer extends BaseConsumer<String, String> {

    ThirdpartyShippingMapper thirdpartyShippingMapper;

    public DbConsumer(List<String> topics, String groupId, List<TopicPartition> targetPartitions ) {
        super(topics, groupId, targetPartitions);
    }

    public void setThirdpartyShippingMapper(ThirdpartyShippingMapper thirdpartyShippingMapper) {
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
                 //  模拟消费
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
