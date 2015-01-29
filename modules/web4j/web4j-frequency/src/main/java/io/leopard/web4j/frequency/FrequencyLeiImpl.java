package io.leopard.web4j.frequency;

import io.leopard.core.exception.ConnectionLimitException;
import io.leopard.data4j.redis.Redis;

public class FrequencyLeiImpl implements FrequencyLei {

	private Redis redis;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	protected String getKey(String user, String uri) {
		return "ul:" + user + ":" + uri;
	}

	@Override
	public void request(String user, String uri, int seconds) throws FrequencyException {
		String key = this.getKey(user, uri);
		Long result = redis.setnx(key, "");
		if (result == 0) {
			throw new ConnectionLimitException(user, uri);
		}
		redis.expire(key, seconds);
	}
}
