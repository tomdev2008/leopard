package io.leopard.test4j.mock.dao;

import io.leopard.data4j.memdb.Memdb;
import io.leopard.jdbc.Jdbc;
import io.leopard.memcache.Memcache;
import io.leopard.redis.Redis;
import io.leopard.test4j.mock.transaction.RedisTransactionImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstanceDaoRedisImplTest {
	public static class TestDaoTestImpl {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
		public Memdb memdb;
	}

	public static class TestDaoTestImpl2 {
		public Jdbc jdbc;
		public Memcache memcache;
		public Memdb memdb;
	}

	@Test
	public void instance() {
		InstanceDaoRedisImpl instanceDaoRedisImpl = Mockito.spy(new InstanceDaoRedisImpl());
		TestDaoTestImpl testDao = instanceDaoRedisImpl.instance(TestDaoTestImpl.class);
		Assert.assertTrue(testDao.redis instanceof RedisTransactionImpl);
		// Mock.verify(instanceDaoRedisImpl, 1).mockRedis(Mockito.anyObject(), Mockito.any(Field.class));
	}

	@Test
	public void instance2() {
		InstanceDaoRedisImpl instanceDaoRedisImpl = Mockito.spy(new InstanceDaoRedisImpl());
		TestDaoTestImpl2 testDao = instanceDaoRedisImpl.instance(TestDaoTestImpl2.class);
		Assert.assertNotNull(testDao);
		// Mock.verify(instanceDaoRedisImpl, 0).mockRedis(Mockito.anyObject(), Mockito.any(Field.class));
	}
}