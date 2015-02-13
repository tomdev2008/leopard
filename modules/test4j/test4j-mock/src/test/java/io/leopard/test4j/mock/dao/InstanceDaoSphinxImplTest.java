package io.leopard.test4j.mock.dao;

import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.data4j.jdbc.test.JdbcH2Impl;
import io.leopard.data4j.memcache.Memcache;
import io.leopard.data4j.memdb.Memdb;
import io.leopard.data4j.redis.Redis;

import org.junit.Assert;
import org.junit.Test;

public class InstanceDaoSphinxImplTest {
	public static class TestDaoTestImpl {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
		public Memdb memdb;
	}

	@Test
	public void instance() {
		InstanceDaoSphinxImpl instanceDaoSphinxImpl = new InstanceDaoSphinxImpl();
		TestDaoTestImpl testDao = instanceDaoSphinxImpl.instance(TestDaoTestImpl.class);
		Assert.assertFalse(testDao.jdbc instanceof JdbcH2Impl);
	}

}