package io.leopard.biz.sortedset;

import io.leopard.data4j.redis.RedisMemoryImpl;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.Tuple;

public class SortedSetBizRedisImplTest {

	protected SortedSetBizRedisImpl sortedSetBizRedisImpl = new SortedSetBizRedisImpl(new RedisMemoryImpl(), "key");

	@Test
	public void zadd() {
		sortedSetBizRedisImpl.zadd("element", 1);
		Assert.assertEquals(1.0d, sortedSetBizRedisImpl.zscore("element"), 0);
		sortedSetBizRedisImpl.zrem("element");
		Assert.assertNull(sortedSetBizRedisImpl.zscore("element"));
	}

	@Test
	public void listAll() {
		sortedSetBizRedisImpl.zadd("element1", 1);
		sortedSetBizRedisImpl.zadd("element2", 1);
		Set<Tuple> set = this.sortedSetBizRedisImpl.listAll();

		Assert.assertEquals(2, set.size());
	}

}