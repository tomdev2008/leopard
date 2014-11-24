package io.leopard.test.api;

public class DaoTesterMemoryImpl implements DaoTester {

	// private Redis redis = Mockito.mock(Redis.class);

	@Override
	public void start(Class<?> clazz) throws Exception {
		Object dao = clazz.newInstance();
		// LeopardMockito.setProperty(dao, redis);
		DaoTesterAssert.start(dao);
	}

}
