package io.leopard.data4j.redis.util;

import io.leopard.data4j.redis.RedisImpl;

import org.junit.Assert;
import org.junit.Test;

public class RedisFactoryTest {

	@Test
	public void create() throws Exception {
		RedisImpl redis = RedisFactory.create("server");
		Assert.assertEquals("server", redis.getServerInfo());
		// Assert.assertTrue(redis.equals(this.redis));
	}

	@Test
	public void RedisFactory() {
		new RedisFactory();
	}
}