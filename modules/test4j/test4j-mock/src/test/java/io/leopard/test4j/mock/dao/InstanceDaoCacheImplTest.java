package io.leopard.test4j.mock.dao;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.test.JdbcH2Impl;
import io.leopard.memcache.Memcache;
import io.leopard.redis.Redis;
import io.leopard.test4j.mock.transaction.MemcacheTransactionImpl;
import io.leopard.test4j.mock.transaction.RedisTransactionImpl;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cache.CacheManager;

public class InstanceDaoCacheImplTest {
	public static class TestObject {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
	}

	public static class TestDaoTestImpl {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
	}

	public static class TestDaoTestImpl2 {
		public Jdbc jdbc;
		public Memcache memcache;
		public TestObject newsDaoHttpImpl;
	}

	@Test
	public void instance() {
		InstanceDaoCacheImpl instanceDaoCacheImpl = new InstanceDaoCacheImpl();
		TestDaoTestImpl testDao = instanceDaoCacheImpl.instance(TestDaoTestImpl.class);
		Assert.assertTrue(testDao.jdbc instanceof JdbcH2Impl);
		Assert.assertTrue(testDao.memcache instanceof MemcacheTransactionImpl);
		Assert.assertTrue(testDao.redis instanceof RedisTransactionImpl);
	}

	@Test
	public void instance2() {
		InstanceDaoCacheImpl instanceDaoCacheImpl = Mockito.spy(new InstanceDaoCacheImpl());
		Mockito.doNothing().when(instanceDaoCacheImpl).mockOther(Mockito.anyObject(), Mockito.any(Field.class));

		TestDaoTestImpl2 testDao = instanceDaoCacheImpl.instance(TestDaoTestImpl2.class);
		Assert.assertTrue(testDao.jdbc instanceof JdbcH2Impl);
		Assert.assertTrue(testDao.memcache instanceof MemcacheTransactionImpl);
		// Mock.verify(instanceDaoCacheImpl, 1).mockOther(Mockito.anyObject(), Mockito.any(Field.class));
	}

	@Test
	public void isIgnoreType() {
		InstanceDaoCacheImpl instanceDaoCacheImpl = new InstanceDaoCacheImpl();
		Assert.assertTrue(instanceDaoCacheImpl.isIgnoreType(String.class));
		Assert.assertTrue(instanceDaoCacheImpl.isIgnoreType(CacheManager.class));
		// Assert.assertTrue(instanceDaoCacheImpl.isIgnoreType(Counter.class));
	}

	@Test
	public void mockOther() {
		// AUTO
	}

}