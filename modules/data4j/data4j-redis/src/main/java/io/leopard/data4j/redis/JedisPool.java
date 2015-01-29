package io.leopard.data4j.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.AbandonedConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisPool extends redis.clients.jedis.JedisPool {

	protected RedisConnectionListener redisConnectionListener;

	public JedisPool(final JedisPoolConfig poolConfig, final String host, final int port, final int timeout) {
		super(poolConfig, host, port, timeout);
		this.initRedisConnectionListener(poolConfig, host, port, timeout);

		AbandonedConfig abandonedConfig = new AbandonedConfig();
		abandonedConfig.setRemoveAbandonedTimeout(10);
		abandonedConfig.setRemoveAbandonedOnBorrow(true);
		abandonedConfig.setRemoveAbandonedOnMaintenance(true);
		internalPool.setAbandonedConfig(abandonedConfig);
	}

	protected void initRedisConnectionListener(JedisPoolConfig poolConfig, String host, int port, int timeout) {
		String className = System.getProperty(RedisConnectionListener.class.getName());
		if (StringUtils.isEmpty(className)) {
			return;
		}
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			return;
		}
		try {
			redisConnectionListener = (RedisConnectionListener) clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		int maxActive = ((JedisPoolConfig) poolConfig).getMaxTotal();
		// GenericObjectPool<Jedis> pool = this.getInternalPool();
		redisConnectionListener.setPoolConfig(host, port, timeout, maxActive, super.internalPool);
	}

	@Override
	public Jedis getResource() {
		long startTime = System.nanoTime();
		Jedis resource = null;
		try {
			resource = super.getResource();
		}
		catch (JedisConnectionException e) {
			if (redisConnectionListener != null) {
				redisConnectionListener.broken();
			}
			throw e;
		}
		finally {
			if (redisConnectionListener != null) {
				redisConnectionListener.open(resource, startTime);
			}
		}
		return resource;
	}

	@Override
	public void returnBrokenResource(Jedis resource) {
		try {
			super.returnBrokenResource(resource);
		}
		finally {
			if (redisConnectionListener != null) {
				redisConnectionListener.broken();
			}
		}
	}

	@Override
	public void returnResource(Jedis resource) {
		try {
			super.returnResource(resource);
		}
		finally {
			if (redisConnectionListener != null) {
				redisConnectionListener.close(resource);
			}
		}
	}

	// // protected GenericObjectPool<Jedis> internalPool2;
	//
	// // @SuppressWarnings("unchecked")
	// public GenericObjectPool<Jedis> getInternalPool() {
	// return super.internalPool;
	// // if (internalPool2 != null) {
	// // return internalPool2;
	// // }
	// // Field field = FieldUtils.getDeclaredField(Pool.class, "internalPool",
	// true);
	// // try {
	// // internalPool2 = (GenericObjectPool<Jedis>) field.get(this);
	// // }
	// // catch (Exception e) {
	// // throw new RuntimeException(e.getMessage(), e);
	// // }
	// // return internalPool2;
	// }

}
