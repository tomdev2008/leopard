package io.leopard.test4j.mock.dao;

import io.leopard.data4j.jdbc.Jdbc;

import org.junit.Assert;
import org.junit.Test;

public class DaoInstanceUtilTest {

	@Test
	public void getFullClassName() {
		Assert.assertEquals("io.leopard.test.mock.dao.cache.UserDaoCacheImpl", DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoCacheImpl"));
		Assert.assertEquals("io.leopard.test.mock.dao.redis.UserDaoRedisImpl", DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoRedisImpl"));
		Assert.assertEquals("io.leopard.test.mock.dao.memcached.UserDaoMemcachedImpl", DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoMemcachedImpl"));
		Assert.assertEquals("io.leopard.test.mock.dao.mysql.UserDaoMysqlImpl", DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoMysqlImpl"));
		Assert.assertEquals("io.leopard.test.mock.dao.http.UserDaoHttpImpl", DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoHttpImpl"));
		Assert.assertEquals("io.leopard.test.mock.dao.memory.UserDaoMemoryImpl", DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoMemoryImpl"));
		Assert.assertEquals("io.leopard.test.mock.dao.sphinx.UserDaoSphinxImpl", DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoSphinxImpl"));
		try {
			DaoInstanceUtil.getFullClassName(DaoInstanceUtilTest.class, "userDaoOtherImpl");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void DaoInstanceUtil() {
		new DaoInstanceUtil();
	}

	@Test
	public void isSimpleType() {
		Assert.assertTrue(DaoInstanceUtil.isSimpleType(String.class));
	}

	@Test
	public void getFullClass() {
		try {
			DaoInstanceUtil.getFullClass(DaoInstanceUtilTest.class, "userDaoMysqlImpl");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void newInstance() {

	}

	public static class UserDaoMysqlImpl {
		private Jdbc jdbc;
	}

	@Test
	public void mockAllFields() {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		DaoInstanceUtil.mockAllFields(userDao);

		Assert.assertNotNull(userDao.jdbc);
	}
}