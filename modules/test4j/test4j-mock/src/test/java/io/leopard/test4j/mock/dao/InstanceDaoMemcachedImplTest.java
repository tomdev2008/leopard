package io.leopard.test4j.mock.dao;

import io.leopard.data4j.memdb.Memdb;
import io.leopard.jdbc.Jdbc;
import io.leopard.memcache.Memcache;
import io.leopard.redis.Redis;
import io.leopard.test4j.mock.transaction.MemcacheTransactionImpl;

import org.junit.Assert;
import org.junit.Test;

public class InstanceDaoMemcachedImplTest {
	public static  class TestDaoTestImpl {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
		public Memdb memdb;
	}
	@Test
	public void instance() {
		InstanceDaoMemcachedImpl instanceDaoMemcachedImpl = new InstanceDaoMemcachedImpl();
		TestDaoTestImpl testDao = instanceDaoMemcachedImpl.instance(TestDaoTestImpl.class);
		Assert.assertTrue(testDao.memcache instanceof MemcacheTransactionImpl);
	}

}