package io.leopard.test4j.mock.dao;

import io.leopard.data4j.redis.Redis;
import io.leopard.test4j.mock.transaction.RedisTransactionImpl;

import org.junit.Assert;
import org.junit.Test;

public class InstanceDaoVersionImplTest {

	public static class TestDaoTestImpl {
		public Redis redis;
	}

	@Test
	public void instance() {
		InstanceDaoVersionImpl instanceDaoVersionImpl = new InstanceDaoVersionImpl();
		TestDaoTestImpl testDao = instanceDaoVersionImpl.instance(TestDaoTestImpl.class);
		Assert.assertTrue(testDao.redis instanceof RedisTransactionImpl);
	}

}