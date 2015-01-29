package io.leopard.data4j.redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RedisBaseTest {
	RedisBase redis = new RedisBase();

	@Test
	public void setServer() {
		redis.setServer("server");
		Assert.assertEquals("server", redis.server);
		Redis redisMock = Mockito.mock(Redis.class);
		redis.setRedis(redisMock);
		Assert.assertNotNull(redis.getRedis());

		redis.setLog(true);
		Assert.assertTrue(redis.log);

		redis.setRedisList(new Redis[2]);
		Assert.assertEquals(2, redis.getRedisSize());
		Assert.assertEquals(2, redis.getRedisList().length);
	}

	@Test
	public void destroy() {
		redis.init();
		redis.setServer("server");
		redis.destroy();
	}
}