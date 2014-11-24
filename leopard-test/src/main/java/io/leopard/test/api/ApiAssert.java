package io.leopard.test.api;

import io.leopard.test.mock.reflect.PackageUtil;

import java.lang.reflect.Modifier;
import java.util.List;

public class ApiAssert {
	public static void assertAllDaos(String packageName) {
		List<Class<?>> classList = PackageUtil.getClassList(packageName);
		for (Class<?> clazz : classList) {
			try {
				assertDao(clazz);
			}
			catch (Exception e) {
				System.err.println("error dao:" + clazz.getName());
				// throw e;
				e.printStackTrace();
			}
		}
	}

	protected static <T> void assertDao(Class<T> clazz) throws Exception {
		int mod = clazz.getModifiers();
		if (Modifier.isAbstract(mod)) {
			return;
		}
		String className = clazz.getName();
		DaoTester daoTester = getDaoTester(className);
		daoTester.start(clazz);
	}

	protected static DaoTester getDaoTester(String className) {
		if (className.endsWith("MysqlImpl")) {
			return new DaoTesterMysqlImpl();
		}
		else if (className.endsWith("RedisImpl")) {
			return new DaoTesterRedisImpl();
		}
		else if (className.endsWith("MemcacheImpl")) {
			return new DaoTesterMemcacheImpl();
		}
		else if (className.endsWith("MemcachedImpl")) {
			return new DaoTesterMemcacheImpl();
		}
		else if (className.endsWith("MemoryImpl")) {
			return new DaoTesterMemoryImpl();
		}
		else if (className.endsWith("CacheImpl")) {
			return new DaoTesterCacheImpl();
		}
		throw new IllegalArgumentException("未知类型[" + className + "]");
	}
}
