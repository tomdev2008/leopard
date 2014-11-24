package io.leopard.ext.connectionlimit;

import io.leopard.core.exception.ConnectionLimitException;
import io.leopard.data4j.redis.Redis;

public class ConnectionLimitDaoRedisImpl implements ConnectionLimitDao {

	private static int seconds = 3;//

	private Redis redis;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public static int getSeconds() {
		return seconds;
	}

	public static void setSeconds(int seconds) {
		ConnectionLimitDaoRedisImpl.seconds = seconds;
	}

	protected String getKey(String user, String uri) {
		return "ul:" + user + ":" + uri;
	}

	@Override
	public void request(String user, String uri) throws ConnectionLimitException {
		String key = this.getKey(user, uri);
		// System.err.println("request key:" + key);
		Long result = redis.setnx(key, "");
		if (result == 0) {
			throw new ConnectionLimitException(user, uri);
		}
		redis.expire(key, seconds);
		// boolean exists = redis.exists(key);
		// if (exists) {
		// throw new ConnectionLimitException(user, uri);
		// }
		// redis.setex(key, seconds, "");
	}
}
