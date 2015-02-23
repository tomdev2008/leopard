package io.leopard.test.api;

import io.leopard.redis.Redis;
import io.leopard.test4j.mock.LeopardMockito;

import org.mockito.Mockito;

public class DaoTesterRedisImpl implements DaoTester {

	private Redis redis = Mockito.mock(Redis.class);

	@Override
	public void start(Class<?> clazz) throws Exception {
		Object dao = clazz.newInstance();
		LeopardMockito.setProperty(dao, redis);
		DaoTesterAssert.start(dao);
	}

}
