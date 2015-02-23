package io.leopard.test4j.mock.dao;

import io.leopard.data4j.memdb.Memdb;
import io.leopard.jdbc.Jdbc;
import io.leopard.memcache.Memcache;
import io.leopard.redis.Redis;
import io.leopard.test4j.mock.transaction.MemdbTransactionImpl;

import org.junit.Assert;
import org.junit.Test;

public class InstanceDaoMemoryImplTest {
	public static  class TestDaoTestImpl {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
		public Memdb memdb;
	}
	@Test
	public void instance() {
		InstanceDaoMemoryImpl instanceDaoMemoryImpl = new InstanceDaoMemoryImpl();
		TestDaoTestImpl testDao = instanceDaoMemoryImpl.instance(TestDaoTestImpl.class);
		Assert.assertTrue(testDao.memdb instanceof MemdbTransactionImpl);
	}

}