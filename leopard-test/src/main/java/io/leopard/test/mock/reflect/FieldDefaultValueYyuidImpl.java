package io.leopard.test.mock.reflect;

import java.lang.reflect.Field;

public class FieldDefaultValueYyuidImpl implements FieldDefaultValue {

	@Override
	public Object get(Field field, String fieldName) {
		return 1L;
	}

}
