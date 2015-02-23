package io.leopard.test4j.mock.transaction;

import io.leopard.jdbc.Jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class Redis2TransactionImplTest {
	//
	private Redis2TransactionImpl redis = newInstance();

	private static Redis2TransactionImpl newInstance() {
		Redis2TransactionImpl redis = new Redis2TransactionImpl();
		redis.jdbc = Mockito.mock(Jdbc.class);
		return redis;
	}

	@Test
	public void hset() {
		Assert.assertEquals(1L, (long) (Long) redis.hset("key", "field", "value"));
	}

	@Test
	public void zadd() {
		Assert.assertEquals(1L, (long) (Long) redis.zadd("key", 1, "member"));
	}

}