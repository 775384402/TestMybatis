package com.zwkj.ceng.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zwkj.ceng.apollo.bean.RedissionConfigBean;

/**
 * Created by zwhd on 2020/3/26
 */
@Configuration
public class RedisConfig {

	public static final String BLOCKING_QUEUE = "blockingqueue";
	public static final String DELAY_QUEUE = "delayqueue";

	@Autowired
	RedissionConfigBean redissionConfigBean;

	@Bean(name = BLOCKING_QUEUE)
	public RBlockingQueue<String> rBlockingQueueOrder(RedissonClient redissonClient) {
		return redissonClient.getBlockingQueue(this.getClass().getSimpleName());
	}

	@Bean(name = DELAY_QUEUE)
	public RDelayedQueue<String> rDelayedQueueOrder(RedissonClient redissonClient) {
		return redissonClient.getDelayedQueue(rBlockingQueueOrder(redissonClient));
	}

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson() throws IOException {
		Config config = new Config();
		// single
		SingleServerConfig useSingleServer = config.useSingleServer();
		useSingleServer.setPingConnectionInterval(50000);
		useSingleServer.setTimeout(5000);
		useSingleServer.setTcpNoDelay(true);
		useSingleServer.setKeepAlive(true);
		useSingleServer.setAddress(redissionConfigBean.getUrl());
//		clusterServersConfig.setPassword(redissionConfigBean.getPassword());

		return Redisson.create(config);
	}

}
