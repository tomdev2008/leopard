package io.leopard.test.api;

import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.test4j.mock.LeopardMockito;

import org.mockito.Mockito;

public class DaoTesterMysqlImpl implements DaoTester {

	private Jdbc jdbc = Mockito.mock(Jdbc.class);

	@Override
	public void start(Class<?> clazz) throws Exception {
		Object dao = clazz.newInstance();
		LeopardMockito.setProperty(dao, jdbc);
		DaoTesterAssert.start(dao);
	}
}
