package io.leopard.test.mock.reflect;

import java.lang.reflect.Field;

public class FieldDefaultValuePassportImpl implements FieldDefaultValue {

	@Override
	public Object get(Field field, String fieldName) {
		return "hctan";
	}

}
