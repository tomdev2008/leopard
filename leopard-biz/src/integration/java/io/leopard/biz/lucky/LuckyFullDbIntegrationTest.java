package io.leopard.biz.lucky;

import io.leopard.data4j.redis.Redis;
import io.leopard.data4j.redis.RedisMemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class LuckyFullDbIntegrationTest {

	private Redis redis = new RedisMemoryImpl();

	@Test
	public void add() {
		LuckyFullDb fullDb = new LuckyFullDb(redis, "lucky_test");
		fullDb.add("hctan1", 1);
		Assert.assertEquals(1, fullDb.getWeight("hctan1"));
		Assert.assertEquals(1, fullDb.count());
		fullDb.delete("hctan1");
		Assert.assertEquals(-1, fullDb.getWeight("hctan1"));
		Assert.assertEquals(0, fullDb.count());
	}

	@Test
	public void random() {
		LuckyFullDb fullDb = new LuckyFullDb(redis, "lucky_test");
		fullDb.clean();
		for (int i = 0; i < 3; i++) {
			String member = "hctan" + i;
			fullDb.add(member, 1);
			Assert.assertNotNull(fullDb.random());
		}
	}
}