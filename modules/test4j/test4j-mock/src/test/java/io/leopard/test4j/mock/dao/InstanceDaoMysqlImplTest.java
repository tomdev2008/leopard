package io.leopard.test4j.mock.dao;

import io.leopard.data4j.memdb.Memdb;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.test.JdbcH2Impl;
import io.leopard.memcache.Memcache;
import io.leopard.redis.Redis;

import org.junit.Assert;
import org.junit.Test;

public class InstanceDaoMysqlImplTest {
	public static  class TestDaoTestImpl {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
		public Memdb memdb;
	}
	@Test
	public void instance() {
		InstanceDaoMysqlImpl instanceDaoMysqlImpl = new InstanceDaoMysqlImpl();
		TestDaoTestImpl testDao = instanceDaoMysqlImpl.instance(TestDaoTestImpl.class);
		Assert.assertTrue(testDao.jdbc instanceof JdbcH2Impl);
	}

}