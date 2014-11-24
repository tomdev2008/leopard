package io.leopard.biz.lucky;

import io.leopard.data4j.redis.Redis;
import io.leopard.data4j.redis.RedisMemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class LuckySetDbIntegrationTest {
	private Redis redis = new RedisMemoryImpl();

	@Test
	public void add() {
		LuckySetDb setDb = new LuckySetDb(redis, "lucky_test", 1);
		setDb.add("hctan1");
		Assert.assertTrue(setDb.exist("hctan1"));
		Assert.assertEquals(1, setDb.count());
		setDb.delete("hctan1");
		Assert.assertFalse(setDb.exist("hctan1"));
		Assert.assertEquals(0, setDb.count());
	}

}