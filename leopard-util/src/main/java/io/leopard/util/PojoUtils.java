package io.leopard.util;

public class PojoUtils {

	public static <T> T getInt(T bean) {
		return getInt(bean, -1);
	}

	public static <T> T getInt(T bean, int defaultValue) {
		return bean;
	}
}
