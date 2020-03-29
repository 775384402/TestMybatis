//package com.zwkj.ceng.lock;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RedisLock {
//
//	@Resource
//	@Qualifier("redisTemplateCustomize")
//	private RedisTemplate<String, Object> redisTemplate;
//	/**
//	 * 重试时间
//	 */
//	private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;
//
//	/**
//	 * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁
//	 */
//	private int expireMsecs = 60 * 1000;
//	/**
//	 * 线程获取锁的等待时间
//	 */
//	private int timeoutMsecs = 10 * 1000;
//	/**
//	 * 是否锁定标志
//	 */
//	private volatile boolean locked = false;
//
//	/**
//	 * 获取值
//	 *
//	 * @param key
//	 * @return
//	 */
//	private String get(final String key) {
//		Object obj = redisTemplate.opsForValue().get(key);
//		return obj != null ? obj.toString() : null;
//	}
//
//	/**
//	 * 存入值
//	 *
//	 * @param key
//	 * @param value
//	 * @return
//	 */
//	private boolean setNX(final String key, final String value) {
//		return redisTemplate.opsForValue().setIfAbsent(key, value);
//	}
//
//	/**
//	 * 设置值并返回旧值
//	 *
//	 * @param key
//	 * @param value
//	 * @return
//	 */
//	private String getSet(final String key, final String value) {
//		Object obj = redisTemplate.opsForValue().getAndSet(key, value);
//		return obj != null ? (String) obj : null;
//	}
//
//	/**
//	 * 获取锁，获取失败的话过100ms重试，总超时时间 10 * 1000 ms
//	 *
//	 * @return 获取锁成功返回ture，超时返回false
//	 * @throws InterruptedException
//	 */
//	public synchronized boolean lockRetry(String lockKey) {
//		int timeout = timeoutMsecs;
//		while (timeout >= 0) {
//			long expires = System.currentTimeMillis() + expireMsecs + 1;
//			String expiresStr = String.valueOf(expires); // 锁到期时间
//			if (this.setNX(lockKey, expiresStr)) {
//				locked = true;
//				return true;
//			}
//			// redis里key的时间
//			String currentValue = this.get(lockKey);
//			// 判断锁是否已经过期，过期则重新设置并获取
//			if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
//				// 设置锁并返回旧值
//				String oldValue = this.getSet(lockKey, expiresStr);
//				// 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
//				if (oldValue != null && oldValue.equals(currentValue)) {
//					locked = true;
//					return true;
//				}
//			}
//			timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
//			// 延时
//			try {
//				Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 获取锁，不重试，只获取一次，获取不到就返回失败
//	 *
//	 * @return 获取锁成功返回ture，失败返回false
//	 * @throws InterruptedException
//	 */
//	public synchronized boolean lockNoRetry(String lockKey) {
//		long expires = System.currentTimeMillis() + expireMsecs + 1;
//		String expiresStr = String.valueOf(expires); // 锁到期时间
//		if (this.setNX(lockKey, expiresStr)) {
//			locked = true;
//			return true;
//		}
//		// redis里key的时间
//		String currentValue = this.get(lockKey);
//		// 判断锁是否已经过期，过期则重新设置并获取
//		if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
//			// 设置锁并返回旧值
//			String oldValue = this.getSet(lockKey, expiresStr);
//			// 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
//			if (oldValue != null && oldValue.equals(currentValue)) {
//				locked = true;
//				return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 释放获取到的锁
//	 */
//	public synchronized void unlock(String lockKey) {
//		if (locked) {
//			redisTemplate.delete(lockKey);
//			locked = false;
//		}
//	}
//
//}
