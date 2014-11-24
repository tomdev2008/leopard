package io.leopard.test.api;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ApiClassUtil {
	public static Class<?> getBeanClass(Object dao) {
		Type[] types = getTypes(dao);
		Class<?> beanClazz = (Class<?>) types[0];
		return beanClazz;
	}

	public static Class<?> getKeyClass(Object dao) {
		Type[] types = ApiClassUtil.getTypes(dao);
		Class<?> keyClazz = (Class<?>) types[1];
		return keyClazz;
	}

	public static Type[] getTypes(Object dao) {
		Class<?> daoInterfaceClazz = dao.getClass().getInterfaces()[0];
		// Class<?> superClazz = daoInterfaceClazz.getSuperclass();
		Type[] types = daoInterfaceClazz.getGenericInterfaces();
		ParameterizedType type = (ParameterizedType) types[0];
		return type.getActualTypeArguments();
	}
}
