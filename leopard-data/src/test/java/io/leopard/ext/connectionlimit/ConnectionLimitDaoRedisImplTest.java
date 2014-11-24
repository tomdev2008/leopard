package io.leopard.ext.connectionlimit;

import io.leopard.core.exception.ConnectionLimitException;
import io.leopard.data4j.redis.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ConnectionLimitDaoRedisImplTest {

	protected ConnectionLimitDaoRedisImpl connectionLimitDao = newInstance();

	protected static Redis redis = Mockito.mock(Redis.class);

	protected static ConnectionLimitDaoRedisImpl newInstance() {
		ConnectionLimitDaoRedisImpl connectionLimitDao = new ConnectionLimitDaoRedisImpl();
		connectionLimitDao.setRedis(redis);
		return connectionLimitDao;
	}

	@Test
	public void request() {
		String key = this.connectionLimitDao.getKey("user", "uri");
		Mockito.doReturn(1L).when(redis).setnx(key, "");
		connectionLimitDao.request("user", "uri");

		// Mockito.verify(redis).setex(key, 3, "");

		// Mockito.doReturn(true).when(redis).exists(key);
		Mockito.doReturn(0L).when(redis).setnx(key, "");
		try {
			connectionLimitDao.request("user", "uri");
			Assert.fail("怎么没有抛异常?");
		}
		catch (ConnectionLimitException e) {

		}
	}

	@Test
	public void setSeconds() {
		ConnectionLimitDaoRedisImpl.setSeconds(3);
		Assert.assertEquals(3, ConnectionLimitDaoRedisImpl.getSeconds());
	}
}