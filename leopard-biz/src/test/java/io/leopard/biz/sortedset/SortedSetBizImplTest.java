package io.leopard.biz.sortedset;

import io.leopard.data4j.redis.RedisMemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class SortedSetBizImplTest {

	private RedisMemoryImpl redis = new RedisMemoryImpl();
	protected SortedSetBizImpl sortedSetBizImpl = new SortedSetBizImpl(redis, "key");

	@Test
	public void zadd() {
		sortedSetBizImpl.zadd("element", 1);
		Assert.assertEquals(1.0d, sortedSetBizImpl.zscore("element"), 0);
		sortedSetBizImpl.zrem("element");
		Assert.assertNull(sortedSetBizImpl.zscore("element"));
	}

	@Test
	public void getSortedSetBizRedisImpl() {
		Assert.assertNotNull(this.sortedSetBizImpl.getSortedSetBizRedisImpl());
	}

	@Test
	public void getSortedSetBizMemoryImpl() {
		Assert.assertNotNull(this.sortedSetBizImpl.getSortedSetBizMemoryImpl());
	}

	@Test
	public void listAll() {
		sortedSetBizImpl.zadd("element", 1);
		Assert.assertNotNull(sortedSetBizImpl.listAll());
	}

	@Test
	public void subscribe() {
		String message = "element" + SortedSetBizImpl.SPLIT + "1234";
		sortedSetBizImpl.subscribe(message, false);
		Assert.assertEquals(1, this.sortedSetBizImpl.getSortedSetBizMemoryImpl().listAll().size());
		sortedSetBizImpl.subscribe(message, true);
	}

	@Test
	public void load() {
		redis.del("key");
		redis.zadd("key", 1, "member1");
		redis.zadd("key", 2, "member2");
		sortedSetBizImpl.load();
	}

}