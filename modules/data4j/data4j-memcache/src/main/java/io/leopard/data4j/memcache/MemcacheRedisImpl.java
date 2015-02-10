package io.leopard.data4j.memcache;

import io.leopard.burrow.util.NumberUtil;
import io.leopard.data4j.redis.AbstractRedis;
import io.leopard.data4j.redis.Redis;
import io.leopard.data4j.redis.RedisImpl;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

/**
 * Memcache接口基于Redis的实现.
 * 
 * @author 阿海
 * 
 */
public class MemcacheRedisImpl extends AbstractRedis implements Memcache {
	protected Redis redis;
	private String server;

	// public MemcacheRedisImpl() {
	// new Exception().printStackTrace();
	// }

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return server;
	}

	public Redis getRedis() {
		return redis;
	}

	@Override
	public void init() {
		// System.err.println("MemcacheRedisImpl init");
		if (StringUtils.isEmpty(server)) {
			throw new IllegalArgumentException("属性server没有设置.");
		}
		this.redis = new RedisImpl(server, this.maxActive, this.initialPoolSize, false, this.timeout);
		redis.init();
		// System.err.println("MemcacheRedisImpl.redis:" + this.redis);
	}

	@Override
	public void destroy() {
		if (redis != null) {
			this.redis.destroy();
		}
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#remove(String)
	 */
	public boolean remove(String key) {
		Long result = this.redis.del(key);
		return NumberUtil.toBool(result);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, String)
	 */
	public boolean put(String key, String value) {
		String result = this.redis.set(key, value);
		return StringUtils.isNotEmpty(result);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, String, int )
	 */
	public boolean put(String key, String value, int seconds) {
		String result = this.redis.set(key, value, seconds);
		return StringUtils.isNotEmpty(result);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, int)
	 */
	public boolean put(String key, int value) {
		return this.put(key, Integer.toString(value));
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, int, int)
	 */
	public boolean put(String key, int value, int seconds) {
		return this.put(key, Integer.toString(value), seconds);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#get(String)
	 */
	public String get(String key) {
		String value = this.redis.get(key);
		return value;
	}

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#get(String, Class<BEAN>)
	// */
	// public <BEAN> BEAN get(String key, Class<BEAN> clazz) {
	// String json = this.get(key);
	// return Json.toObject(json, clazz);
	// }

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#putObject(String, Object)
	// */
	// public boolean putObject(String key, Object obj) {
	// throw new NotImplementedException("未实现");
	// }
	//
	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#putObject(String, Object, int)
	// */
	// public boolean putObject(String key, Object obj, int seconds) {
	// throw new NotImplementedException("未实现");
	// }
	//
	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#getObject(String )
	// */
	// public Object getObject(String key) {
	// throw new NotImplementedException("未实现");
	// }

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#flushAll()
	 */
	public boolean flushAll() {
		throw new NotImplementedException("未实现");
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#incr(String)
	 */
	public long incr(String key) {
		Long result = this.redis.incr(key);
		return result;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#addOrIncr(String, long)
	 */
	public long addOrIncr(String key, long num) {
		Long result = this.redis.incrBy(key, num);
		return result;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#getInt(String)
	 */
	public int getInt(String key) {
		String value = this.get(key);
		if (StringUtils.isEmpty(value)) {
			return -1;
		}
		else {
			return Integer.parseInt(value);
		}
	}

	//
	// protected boolean toBool(Long num) {
	// if (num == null || num <= 0) {
	// return false;
	// }
	// else {
	// return true;
	// }
	// }

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#mget(String[])
	 */
	public List<String> mget(String[] keys) {
		return this.redis.mget(keys);
	}

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#mget(String[], Class<BEAN>)
	// */
	// public <BEAN> List<BEAN> mget(String[] keys, Class<BEAN> clazz) {
	// List<String> jsonList = this.mget(keys);
	// return Json.toObject(jsonList, clazz);
	// }

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#add(String, String)
	 */
	public boolean add(String key, String value) {
		return this.put(key, value);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#add(String, String, int)
	 */
	public boolean add(String key, String value, int seconds) {
		return this.put(key, value, seconds);
	}
}
