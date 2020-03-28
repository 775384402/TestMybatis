package com.zwkj.ceng.config;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by zwhd on 2020/3/26
 */
@Configuration
public class RedisConfig {

    public static final String BLOCKING_QUEUE = "blockingqueue";
    public static final String DELAY_QUEUE = "delayqueue";

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
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        clusterServersConfig.setPingConnectionInterval(50000);
        clusterServersConfig.setTimeout(5000);
        clusterServersConfig.setTcpNoDelay(true);
        clusterServersConfig.setKeepAlive(true);
        clusterServersConfig.setMasterConnectionPoolSize(32);
        clusterServersConfig.setSlaveConnectionPoolSize(16);
        clusterServersConfig.setMasterConnectionMinimumIdleSize(5);
        clusterServersConfig.setSlaveConnectionMinimumIdleSize(3);
        clusterServersConfig.addNodeAddress("http://122.51.239.204:6379");
        return Redisson.create(config);
    }


}
