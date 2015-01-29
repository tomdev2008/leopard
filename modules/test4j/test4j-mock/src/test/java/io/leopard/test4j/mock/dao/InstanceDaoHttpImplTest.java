package io.leopard.test4j.mock.dao;

import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.data4j.memcache.Memcache;
import io.leopard.data4j.memdb.Memdb;
import io.leopard.data4j.redis.Redis;

import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

//@RunWith(LeopardMockRunner.class)
@PrepareForTest({ DaoInstanceUtil.class })
public class InstanceDaoHttpImplTest {
	public static class TestDaoTestImpl {
		public Jdbc jdbc;
		public Redis redis;
		public Memcache memcache;
		public Memdb memdb;
	}

	@Test
	public void instance() {
		InstanceDaoHttpImpl instanceDaoHttpImpl = new InstanceDaoHttpImpl();
		instanceDaoHttpImpl.instance(TestDaoTestImpl.class);
		// Assert.assertTrue(testDao.jdbc instanceof JdbcH2Impl);
	}
}