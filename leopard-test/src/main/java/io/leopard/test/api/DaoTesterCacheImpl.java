package io.leopard.test.api;

import io.leopard.test4j.mock.LeopardMockito;

import java.lang.reflect.Field;

import org.mockito.Mockito;

public class DaoTesterCacheImpl implements DaoTester {

	// private Redis redis = Mockito.mock(Redis.class);

	@Override
	public void start(Class<?> clazz) throws Exception {
		Object dao = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Class<?> type = field.getType();
			if (!type.getName().endsWith("Dao")) {
				continue;
			}
			Object mock = Mockito.mock(type);
			LeopardMockito.setProperty(dao, mock, field.getName());
		}
		DaoTesterAssert.start(dao);
	}

}
