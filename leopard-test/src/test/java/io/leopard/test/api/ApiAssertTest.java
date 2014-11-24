package io.leopard.test.api;

import io.leopard.data4j.cache.api.IDelete;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class ApiAssertTest {

	@Test
	public void ApiAssert() {
		new ApiAssert();
	}

	@Test
	public void getDaoTester() {

		// else if (className.endsWith("RedisImpl")) {
		// return new DaoTesterRedisImpl();
		// }
		// else if (className.endsWith("MemcacheImpl")) {
		// return new DaoTesterMemcacheImpl();
		// }
		// else if (className.endsWith("MemcachedImpl")) {
		// return new DaoTesterMemcacheImpl();
		// }
		// else if (className.endsWith("MemoryImpl")) {
		// return new DaoTesterMemoryImpl();
		// }
		// else if (className.endsWith("CacheImpl")) {
		// return new DaoTesterCacheImpl();
		// }
		// throw new IllegalArgumentException("未知类型[" + className + "]");
		Assert.assertTrue(ApiAssert.getDaoTester("UserDaoMysqlImpl") instanceof DaoTesterMysqlImpl);
		Assert.assertTrue(ApiAssert.getDaoTester("UserDaoRedisImpl") instanceof DaoTesterRedisImpl);
		Assert.assertTrue(ApiAssert.getDaoTester("UserDaoMemcacheImpl") instanceof DaoTesterMemcacheImpl);
		Assert.assertTrue(ApiAssert.getDaoTester("UserDaoMemcachedImpl") instanceof DaoTesterMemcacheImpl);
		Assert.assertTrue(ApiAssert.getDaoTester("UserDaoMemoryImpl") instanceof DaoTesterMemoryImpl);
		Assert.assertTrue(ApiAssert.getDaoTester("UserDaoCacheImpl") instanceof DaoTesterCacheImpl);

		try {
			ApiAssert.getDaoTester("UserDaoOtherImpl");
			Assert.fail("为什么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	public static interface UserDao extends IDelete<String, String> {

	}

	public static class UserDaoRedisImpl implements UserDao {

		@Override
		public boolean delete(String key, String opusername, Date lmodify) {
			
			return false;
		}

		@Override
		public String get(String key) {
			
			return null;
		}

		@Override
		public boolean add(String bean) {
			
			return false;
		}

	}

	@Test
	public void assertDao() throws Exception {
		ApiAssert.assertDao(UserDaoRedisImpl.class);
	}
}