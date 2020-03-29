package com.zwkj.ceng.kafka.consumer;

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

import com.zwkj.ceng.kafka.dao.TopicPartitionInfo;

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

	private final Map<TopicPartition, TopicPartitionInfo> topicPartitionInfos = new HashMap<>();

	public BaseConsumer(List<String> topics, String groupId, List<TopicPartition> targetPartitions) {
		consumer = new KafkaConsumer<>(getProperties(groupId));
		this.topics = topics;
		this.targetPartitions = targetPartitions;
	}

	@Override
	public void run() {
		final Map<TopicPartition, Long> partitionUncommitOffersetMap = new HashMap<TopicPartition, Long>();

		chooseTopic(partitionUncommitOffersetMap);

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
					TopicPartitionInfo topicPartitionInfo = topicPartitionInfos.get(map.getKey());
					if (topicPartitionInfo != null) {
						topicPartitionInfo.setCurrentOffset(offerSet);
					}
				}
				consumer.commitSync(offsets);
				partitionUncommitOffersetMap.clear();
			}

		}
	}

	protected abstract void doCall(ConsumerRecords<String, String> records,
			Map<TopicPartition, Long> partitionUncommitOffersetMap);

	private void chooseTopic(Map<TopicPartition, Long> partitionToUncommittedOffsetMap) {
		if (CollectionUtils.isEmpty(targetPartitions)) {
			consumer.subscribe(topics, new CsmRebalanceListener(partitionToUncommittedOffsetMap));
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
			consumer.close();
		} catch (Exception e) {
			// TODO: handle exception
			log.debug(" consumer.wakeup error ");
		}

	}

	class CsmRebalanceListener implements ConsumerRebalanceListener {
		private final Map<TopicPartition, Long> partitionToUncommittedOffsetMap;

		public CsmRebalanceListener(Map<TopicPartition, Long> partitionToUncommittedOffsetMap) {
			this.partitionToUncommittedOffsetMap = partitionToUncommittedOffsetMap;
		}

		@Override
		public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
			for (TopicPartition tp : partitions)
				topicPartitionInfos.remove(tp);
			if (partitionToUncommittedOffsetMap != null) {
				// consumer.commit
			}
		}

		@Override
		public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
			for (TopicPartition tp : partitions) {
				OffsetAndMetadata offsetAndMetaData = consumer.committed(tp);
				long startOffset = 0L;
				if (offsetAndMetaData != null) {
					startOffset = offsetAndMetaData.offset();
				}
				topicPartitionInfos.put(tp, new TopicPartitionInfo(startOffset, tp, this.getClass().getSimpleName()));
			}
		}
	}

}
