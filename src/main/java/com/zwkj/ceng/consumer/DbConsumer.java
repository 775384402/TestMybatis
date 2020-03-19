package com.zwkj.ceng.consumer;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

public class DbConsumer extends BaseConsumer<String, String> {

	public DbConsumer(List<String> topics, String groupId, List<TopicPartition> targetPartitions) {
		super(topics, groupId, targetPartitions);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doCall(ConsumerRecords<String, String> records,
			Map<TopicPartition, Long> partitionUncommitOffersetMap) {
		// TODO Auto-generated method stub
		for (ConsumerRecord<String, String> record : records) {
			System.out.println("record . topic {" + record.topic() + "}  ; partition  {" + record.partition()
					+ "} ; offset {" + record.offset() + "}  ");
			partitionUncommitOffersetMap.put(new TopicPartition(record.topic(), record.partition()), record.offset());
		}
	}

}
