package io.leopard.schema;

import io.leopard.data.env.YtestUtil;
import io.leopard.data.nosql.NosqlMysqlImpl;
import io.leopard.data4j.jdbc.JdbcDataSource;
import io.leopard.data4j.jdbc.JdbcMysqlImpl;
import io.leopard.data4j.jdbc.MysqlDsnDataSource;
import io.leopard.data4j.memcache.MemcacheRedisImpl;
import io.leopard.data4j.memdb.MemdbRsyncImpl;
import io.leopard.data4j.redis.RedisHashImpl;
import io.leopard.data4j.redis.RedisImpl;
import io.leopard.data4j.redis.RedisLog4jImpl;
import io.leopard.data4j.redis.SpringJedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

public class DataSourceClassUtil {

	public static Class<?> findClass(Class<?> clazz) {
		return YtestUtil.findClass(clazz);
	}

	public static Class<?> getJdbcMysqlImpl() {
		return findClass(JdbcMysqlImpl.class);
	}

	public static Class<?> getJdbcDataSource() {
		return findClass(JdbcDataSource.class);
	}

	public static Class<?> getNosqlMysqlImpl() {
		return findClass(NosqlMysqlImpl.class);
	}

	public static Class<?> getMysqlDsnDataSource() {
		return findClass(MysqlDsnDataSource.class);
	}

	public static Class<?> getMemcacheRedisImpl() {
		return findClass(MemcacheRedisImpl.class);
	}

	// public static Class<?> getMemcacheImpl() {
	// return findClass(MemcacheImpl.class);
	// }

	public static Class<?> getMemdbRsyncImpl() {
		return findClass(MemdbRsyncImpl.class);
	}

	public static Class<?> getRedisLog4jImpl() {
		return findClass(RedisLog4jImpl.class);
	}

	public static Class<?> getRedisImpl() {
		return findClass(RedisImpl.class);
	}

	public static Class<?> getRedisHashImpl() {
		return findClass(RedisHashImpl.class);
	}

	public static Class<?> getSpringJedisConnectionFactory() {
		return findClass(SpringJedisConnectionFactory.class);
	}

	public static Class<?> getJedisPoolConfig() {
		return findClass(JedisPoolConfig.class);
	}

}
