package io.leopard.test.api;

import io.leopard.util.ClassTypeUtil;

import java.lang.reflect.Constructor;

public class ApiKeyUtil {

	public static Object getKey(Class<?> keyClazz) {
		Object key = getDefaultValue(keyClazz);

		if (key == null) {
			if (keyClazz.getName().endsWith("Key")) {
				key = newInstance(keyClazz);
			}
			else {
				throw new IllegalArgumentException("未知类型[" + keyClazz + "].");
			}
		}
		return key;
	}

	protected static Object newInstance(Class<?> keyClazz) {
		Constructor<?>[] list = keyClazz.getConstructors();
		for (Constructor<?> c : list) {
			Class<?>[] types = c.getParameterTypes();
			if (types.length == 0) {
				continue;
			}
			try {
				return newInstance(c);
			}
			catch (Exception e) {
				throw new RuntimeException("keyClazz:" + keyClazz.getName() + " msg:" + e.getMessage(), e);
			}
		}
		throw new RuntimeException("Key[" + keyClazz + "]没有带参数的构造函数.");
	}

	protected static Object newInstance(Constructor<?> c) throws Exception {
		Class<?>[] types = c.getParameterTypes();
		Object[] initargs = new Object[types.length];
		for (int i = 0; i < initargs.length; i++) {
			initargs[i] = getDefaultValue(types[i]);
			System.out.println("initargs[i]:" + initargs[i]);
		}
		Object obj = c.newInstance(initargs);
		return obj;
	}

	public static Object getDefaultValue(Class<?> clazz) {
		Object key;
		if (clazz.equals(String.class)) {
			key = "str";
		}
		else if (ClassTypeUtil.isInteger(clazz)) {
			key = 1;
		}
		else if (ClassTypeUtil.isLong(clazz)) {
			key = 1L;
		}
		else {
			key = null;
		}
		return key;
	}
}
