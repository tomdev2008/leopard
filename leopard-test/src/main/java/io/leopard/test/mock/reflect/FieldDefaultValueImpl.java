package io.leopard.test.mock.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldDefaultValueImpl implements FieldDefaultValue {

	private Map<String, FieldDefaultValue> map = new HashMap<String, FieldDefaultValue>();

	public FieldDefaultValueImpl() {
		map.put("gameId", new FieldDefaultValueGameIdImpl());
		map.put("username", new FieldDefaultValuePassportImpl());
		map.put("opusername", new FieldDefaultValuePassportImpl());
		map.put("passport", new FieldDefaultValuePassportImpl());
		map.put("serverId", new FieldDefaultValueServerIdImpl());
		map.put("yyuid", new FieldDefaultValueYyuidImpl());
		map.put("opyyuid", new FieldDefaultValueYyuidImpl());

		// FIELD_DEFAULT_VALUE.put("yyuid", 1L);
		// FIELD_DEFAULT_VALUE.put("opyyuid", 1L);
		// FIELD_DEFAULT_VALUE.put("username", "username");
		// FIELD_DEFAULT_VALUE.put("opusername", "hctan");
		// FIELD_DEFAULT_VALUE.put("gameId", "ddt");
		// FIELD_DEFAULT_VALUE.put("serverId", "s1");
	}

	@Override
	public Object get(Field field, String fieldName) {
		FieldDefaultValue fieldDefaultValue = map.get(fieldName);
		if (fieldDefaultValue == null) {
			return null;
		}
		return fieldDefaultValue.get(field, fieldName);
	}

}
