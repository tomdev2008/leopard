package io.leopard.test4j.mock.transaction;

import io.leopard.burrow.util.ThreadUtil;
import io.leopard.data4j.redis.Redis;
import io.leopard.data4j.redis.RedisMemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class MemcacheTransactionImplTest {

	protected static MemcacheTransactionImpl createMemcache() {
		MemcacheTransactionImpl memcache = new MemcacheTransactionImpl();
		Redis redis = new RedisMemoryImpl();
		// LeopardMockito.setProperty(memcache, redis);
		return memcache;
	}

	@Test
	public void get() {
		MemcacheTransactionImpl memcache = createMemcache();
		System.out.println("redis:" + memcache.redis);
		memcache.add("key", "value");
		Assert.assertEquals("value", memcache.get("key"));
		memcache.remove("key");
		Assert.assertNull(memcache.get("key"));
	}

	@Test
	public void add() {
		MemcacheTransactionImpl memcache = createMemcache();
		memcache.add("key", "value", 1);
		Assert.assertEquals("value", memcache.get("key"));
		ThreadUtil.sleep(1100);
		Assert.assertNull(memcache.get("key"));
	}

	@Test
	public void put() {
		MemcacheTransactionImpl memcache = createMemcache();
		{
			memcache.put("key", "value");
			Assert.assertEquals("value", memcache.get("key"));
		}
		{
			memcache.remove("key");
			memcache.put("key", "value", 1);
			Assert.assertEquals("value", memcache.get("key"));
			ThreadUtil.sleep(1100);
			Assert.assertNull(memcache.get("key"));
		}
		{
			memcache.remove("key");
			memcache.put("key", 1);
			Assert.assertEquals("1", memcache.get("key"));
		}
		{
			memcache.remove("key");
			memcache.put("key", 2, 1);
			Assert.assertEquals("2", memcache.get("key"));
			ThreadUtil.sleep(1100);
			Assert.assertNull(memcache.get("key"));
		}
	}

	@Test
	public void getInt() {
		MemcacheTransactionImpl memcache = createMemcache();
		Assert.assertEquals(-1, memcache.getInt("key"));
		memcache.put("key", 1);
		Assert.assertEquals(1, memcache.getInt("key"));
	}

	@Test
	public void addOrIncr() {
		MemcacheTransactionImpl memcache = createMemcache();
		Assert.assertEquals(2, memcache.addOrIncr("key", 2));
		Assert.assertEquals(2, memcache.getInt("key"));
	}

	@Test
	public void incr() {
		MemcacheTransactionImpl memcache = createMemcache();
		Assert.assertEquals(1, memcache.incr("key"));
		Assert.assertEquals(1, memcache.getInt("key"));
	}

	@Test
	public void flushAll() {
		MemcacheTransactionImpl memcache = createMemcache();
		memcache.flushAll();
	}

}