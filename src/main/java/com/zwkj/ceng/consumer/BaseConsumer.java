package com.zwkj.ceng.consumer;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseConsumer<K extends Serializable, V extends Serializable> implements Runnable {

    @Getter
    protected AtomicBoolean stop = new AtomicBoolean();
    // 消费主题 test-db-topic
    private List<String> topics;

    private KafkaConsumer<String, String> consumer;

    private final long POLL_TIME = 10000;

    private List<TopicPartition> targetPartitions;

    public BaseConsumer(List<String> topics, String groupId, List<TopicPartition> targetPartitions) {
        consumer = new KafkaConsumer<>(getProperties(groupId));
        this.topics = topics;
        this.targetPartitions = targetPartitions;
    }

    @Override
    public void run() {
        final Map<TopicPartition, Long> partitionUncommitOffersetMap = new HashMap<TopicPartition, Long>();

        chooseTopic();

        while (!stop.get()) {
            ConsumerRecords<String, String> records = consumer.poll(POLL_TIME);
            if (records.isEmpty()) {
                consumer.commitSync();
                continue;
            }
            consumer.pause(consumer.assignment());
            doCall(records, partitionUncommitOffersetMap);
            consumer.resume(consumer.assignment());
            if (!partitionUncommitOffersetMap.isEmpty()) {
                Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<TopicPartition, OffsetAndMetadata>();
                for (Entry<TopicPartition, Long> map : partitionUncommitOffersetMap.entrySet()) {
                    long offerSet = map.getValue() + 1L;
                    offsets.put(map.getKey(), new OffsetAndMetadata(offerSet));
                }
                consumer.commitSync(offsets);
                partitionUncommitOffersetMap.clear();
            }

        }
    }

    protected abstract void doCall(ConsumerRecords<String, String> records,
                                   Map<TopicPartition, Long> partitionUncommitOffersetMap);

    private void chooseTopic() {
        if (CollectionUtils.isEmpty(targetPartitions)) {
            consumer.subscribe(topics, new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    System.out.println(partitions);
                }

                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    System.out.println(partitions);
                }
            });
        } else {
            consumer.assign(targetPartitions);
        }
    }

    private Properties getProperties(String groupId) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "122.51.239.204:9092");
        properties.setProperty("group.id", groupId);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("enable.auto.commit", "false");
        properties.put("max.poll.records", "40");
        properties.put("max.poll.interval.ms", 60000);
        return properties;
    }

    public void stop() {
        stop.set(true);
        try {
            consumer.wakeup();
        } catch (Exception e) {
            // TODO: handle exception
            log.debug(" consumer.wakeup error ");
        }

    }

    private class CsmRebalanceListener implements ConsumerRebalanceListener {

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            // TODO Auto-generated method stub

        }

    }
}
