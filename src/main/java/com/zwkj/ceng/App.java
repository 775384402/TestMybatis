package com.zwkj.ceng;

import com.fm.egg.mq.DefaultKafkaConfig;
import com.fm.egg.mq.annotation.EnableKafkaConfig;
import com.fm.egg.mq.registry.ConsumerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zwkj.ceng.lock.RedisConfig;

@SpringBootApplication
@EnableKafkaConfig(serversBrokers = "122.51.239.204:9092", basePackages = "com.zwkj.ceng.consumer")
public class App extends DefaultKafkaConfig {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Value("122.51.239.204:9092")
    private String serversBrokers;

	@Override
	protected void addConsumers(ConsumerRegistry consumerRegistry) {

	}
}
