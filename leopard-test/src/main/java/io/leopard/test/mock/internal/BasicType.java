package io.leopard.test.mock.internal;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BasicType {
	private static Set<String> BASIC_TYPE = new HashSet<String>();
	static {
		BASIC_TYPE.add(String.class.getName());
		BASIC_TYPE.add(int.class.getName());
		BASIC_TYPE.add(Integer.class.getName());
		BASIC_TYPE.add(float.class.getName());
		BASIC_TYPE.add(Float.class.getName());
		BASIC_TYPE.add(long.class.getName());
		BASIC_TYPE.add(Long.class.getName());
		BASIC_TYPE.add(double.class.getName());
		BASIC_TYPE.add(Double.class.getName());
		BASIC_TYPE.add(Date.class.getName());
	}

	public static boolean isBasicType(Class<?> clazz) {
		return BASIC_TYPE.contains(clazz.getName());
	}

	public static boolean isBasicType(List<?> list) {
		for (Object element : list) {
			if (!isBasicType(element.getClass())) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isBasicType(Map<?, ?> map) {
		for (Entry entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (!isBasicType(key.getClass())) {
				return false;
			}
			if (!isBasicType(value.getClass())) {
				return false;
			}
		}
		return true;

	}
}
