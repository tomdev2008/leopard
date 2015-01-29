package io.leopard.data4j.redis.util;

import org.junit.Assert;
import org.junit.Test;

public class RedisPoolTest {

	@Test
	public void RedisPool() {
		RedisPool redisPool = new RedisPool("server:6311", 1);
		redisPool.init();
		Assert.assertEquals("server:6311", redisPool.getServer());
		Assert.assertEquals(1, redisPool.getTimeout());
		redisPool.destroy();
	}

}