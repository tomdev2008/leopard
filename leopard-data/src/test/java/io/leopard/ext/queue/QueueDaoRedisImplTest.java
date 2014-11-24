package io.leopard.ext.queue;

import io.leopard.data4j.redis.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class QueueDaoRedisImplTest {

	protected QueueDaoRedisImpl queueDaoRedisImpl = newInstance();
	protected static Redis redis = Mockito.mock(Redis.class);

	protected static QueueDaoRedisImpl newInstance() {
		QueueDaoRedisImpl queueDaoRedisImpl = new QueueDaoRedisImpl();
		queueDaoRedisImpl.setRedis(redis);
		queueDaoRedisImpl.setKey("key");
		return queueDaoRedisImpl;
	}

	@Test
	public void add() {
		Mockito.doReturn(1L).when(redis).rpush("key", "\"\"");
		Assert.assertTrue(queueDaoRedisImpl.add("") > 0);
	}

	@Test
	public void pop() {
		Mockito.doReturn("\"\"").when(redis).lpop("key");
		Assert.assertEquals("\"\"", queueDaoRedisImpl.pop());
	}

	@Test
	public void count() {
		Mockito.doReturn(1L).when(redis).llen("key");
		Assert.assertEquals(1, queueDaoRedisImpl.count());
	}

	@Test
	public void getKey() {
		QueueDaoRedisImpl queueDao = new QueueDaoRedisImpl();
		try {
			queueDao.getKey();
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

}