package io.leopard.test4j.mock.dao;

public class InstanceUtil {
	public static <T> T instantiateClass(Class<T> clazz) {
		// Class<?> daoInterface = clazz.getInterfaces()[0];
		// System.out.println("instantiateClass clazz:" + daoInterface.getInterfaces()[0]);
		try {
			T bean = clazz.newInstance();
			return bean;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
