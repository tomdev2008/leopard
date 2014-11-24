package io.leopard.test.di;

import io.leopard.data4j.redis.Redis;
import io.leopard.data4j.redis.RedisImpl;

public class RedisBeanInstance implements IBeanInstance {

	@Override
	public Object instance(String beanName) {
		String server = DataSourceUtil.getRedisServer(beanName);
		// System.err.println("instance:" + beanName);
		Redis redis = new RedisImpl(server, 8, 3000);
		redis.init();
		return redis;
	}

}
