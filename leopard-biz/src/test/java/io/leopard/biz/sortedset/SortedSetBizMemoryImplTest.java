package io.leopard.biz.sortedset;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.Tuple;

public class SortedSetBizMemoryImplTest {

	protected SortedSetBizMemoryImpl sortedSetBizMemoryImpl = new SortedSetBizMemoryImpl();

	@Test
	public void zadd() {
		sortedSetBizMemoryImpl.zadd("element", 1);
		Assert.assertEquals(1.0d, sortedSetBizMemoryImpl.zscore("element"), 0);
		sortedSetBizMemoryImpl.zrem("element");
		Assert.assertNull(sortedSetBizMemoryImpl.zscore("element"));
	}

	@Test
	public void listAll() {
		Assert.assertNull(this.sortedSetBizMemoryImpl.listAll());
		sortedSetBizMemoryImpl.zadd("element1", 1);
		sortedSetBizMemoryImpl.zadd("element2", 1);
		Set<Tuple> set = this.sortedSetBizMemoryImpl.listAll();
		Assert.assertEquals(2, set.size());
	}

}