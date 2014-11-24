package io.leopard.test.api;

import io.leopard.data4j.memcache.Memcache;
import io.leopard.test4j.mock.LeopardMockito;

import org.mockito.Mockito;

public class DaoTesterMemcacheImpl implements DaoTester {

	private Memcache memcache = Mockito.mock(Memcache.class);

	@Override
	public void start(Class<?> clazz) throws Exception {
		Object dao = clazz.newInstance();
		LeopardMockito.setProperty(dao, memcache);
		DaoTesterAssert.start(dao);
	}

}
