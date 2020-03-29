//package com.zwkj.ceng.lock;
//
//import java.net.UnknownHostException;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
///**
// * redis配置类
// * 
// * @program: springbootdemo
// * @Date: 2019/1/25 15:20
// * @Author: Mr.Zheng
// * @Description:
// */
//
//@Configuration
//@ConditionalOnClass(RedisOperations.class)
//@EnableConfigurationProperties(RedisProperties.class)
//public class RedisConfig extends CachingConfigurerSupport {
//	@Bean(name = "redisTemplateCustomize")
//	@ConditionalOnMissingBean
//	public RedisTemplate<Object, Object> redisTemplate(
//
//			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
//
//		RedisTemplate<Object, Object> template = new RedisTemplate<>();
//
//		template.setConnectionFactory(redisConnectionFactory);
//
//		return template;
//
//	}
//
//}