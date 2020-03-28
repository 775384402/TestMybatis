//package com.zwkj.ceng.kafka.consumer;
//
//import com.fm.egg.mq.annotation.Consumer;
//import com.fm.egg.mq.common.record.AdvancedRecordsConsumer;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.common.TopicPartition;
//
//import java.util.Map;
//
///**
// * Created by zwhd on 2020/3/20
// */
//
//@Slf4j
//@Consumer(groupId = "groupOne", topic = "Test-Topic-1", properties = "/kafka/shipping-detail-consumer.properties", threadCount = 1)
//public class TestConsumer extends AdvancedRecordsConsumer<String, String> {
//
//
//    public TestConsumer(ConsumerRecords<String, String> records, Map<TopicPartition, Long> partitionToUncommittedOffsetMap, String clientId) {
//        super(records, partitionToUncommittedOffsetMap, clientId);
//    }
//
//    @Override
//    public void doCall(ConsumerRecords<String, String> consumerRecords, Map<TopicPartition, Long> partitionToUncommittedOffsetMap) {
//
//        for (ConsumerRecord<String, String> record : consumerRecords) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String messageBody = record.value();
//            log.debug(" TestResultConsumer  message: " + messageBody);
//        }
//        // 全部处理完成修改偏移量
//        for (ConsumerRecord<String, String> record : consumerRecords) {
//            partitionToUncommittedOffsetMap.put(new TopicPartition(record.topic(), record.partition()), record.offset());
//        }
//    }
//}
