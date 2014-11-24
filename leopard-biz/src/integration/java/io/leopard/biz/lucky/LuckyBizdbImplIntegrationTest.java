package io.leopard.biz.lucky;

import io.leopard.data4j.redis.Redis;
import io.leopard.data4j.redis.RedisMemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class LuckyBizdbImplIntegrationTest {

	private Redis redis = new RedisMemoryImpl();

	private int[] weights = new int[] { 1, 10, 100, 1000, 10000 };

	@Test
	public void add() {
		LuckyBizdbImpl luckyDb = new LuckyBizdbImpl(redis, "lucky_test", weights);
		luckyDb.add("hctan1", 1);
		luckyDb.add("hctan2", 2);
		Assert.assertTrue(luckyDb.exist("hctan1"));

		Assert.assertEquals(2, luckyDb.count());
		Assert.assertEquals(1, luckyDb.count(1));
		Assert.assertEquals(1, luckyDb.count(2));

		luckyDb.delete("hctan1");
		luckyDb.delete("hctan2");
		Assert.assertFalse(luckyDb.exist("hctan1"));
		Assert.assertEquals(0, luckyDb.count());
		Assert.assertEquals(0, luckyDb.count(1));
	}

	@Test
	public void lucky() {
		LuckyBizdbImpl luckyDb = new LuckyBizdbImpl(redis, "lucky_test", weights);
		luckyDb.clean();
		luckyDb.add("hctan0", 0);
		luckyDb.add("hctan1", 1);
		luckyDb.add("hctan2", 2);
		luckyDb.add("hctan3", 3);
		// luckyDb.add("hctan4", 4);
		System.out.println("member1:" + luckyDb.lucky());
		System.out.println("member2:" + luckyDb.lucky());
		System.out.println("member3:" + luckyDb.lucky());

	}

	@Test
	public void random() {

	}

}