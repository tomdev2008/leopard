package io.leopard.test.mock.reflect;

import java.lang.reflect.Field;

public class FieldDefaultValueGameIdImpl implements FieldDefaultValue {

	@Override
	public Object get(Field field, String fieldName) {
		Class<?> clazz = field.getType();
		if (clazz.equals(String.class)) {
			return "ddt";
		}
		return null;
	}

}
