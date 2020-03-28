package com.zwkj.ceng.kafka.product;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class KafkaProducerDemo {
    private final Producer<String, String> kafkaProdcer;
    public final static String TOPIC = "Test-Topic";

    private KafkaProducerDemo() {
        kafkaProdcer = createKafkaProducer();
    }

    private Producer<String, String> createKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "122.51.239.204:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> kafkaProducer = new KafkaProducer<>(props);
        return kafkaProducer;
    }

    void produce() {
        for (int i = 0; i < 60; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final String key = "key : " + i;
            String data = "this test kafka message:" + key;
            kafkaProdcer.send(new ProducerRecord<String, String>(TOPIC, key, data), new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("callback : offset -> " + recordMetadata.offset() +
                            ";  partition ->" + recordMetadata.partition());

                }
            });
        }
    }

    public static void main(String[] args) {
        KafkaProducerDemo kafkaProducerDemo = new KafkaProducerDemo();
        kafkaProducerDemo.produce();
    }

}