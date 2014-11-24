package io.leopard.test.mock.internal;

import io.leopard.test.mock.reflect.MethodUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

public class AssertKeyModel {
	public static void assertKeyModel(Class<?> clazz) {
		// System.err.println("assertKeyModel:" + clazz.getName());
		Object bean;
		try {
			@SuppressWarnings("rawtypes")
			Constructor constructor = clazz.getConstructors()[0];
			Object[] initargs = MethodUtil.getDefaultValue(constructor.getParameterTypes());
			bean = constructor.newInstance(initargs);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (!isValidMethodName(methodName)) {
				continue;
			}
			Object[] args = MethodUtil.getDefaultValue(method.getParameterTypes());
			try {
				// System.out.println("args:" + args);
				method.invoke(bean, args);
			}
			catch (Exception e) {
				System.err.println("method:" + method.toGenericString() + " args:" + StringUtils.join(args));
				throw new RuntimeException(e.toString(), e);
			}
		}
		assertKeyModel2(clazz);
	}

	protected static void assertKeyModel2(Class<?> clazz) {

		// System.err.println("assertKeyModel:" + clazz.getName());
		try {
			// @SuppressWarnings("rawtypes")
			Constructor<?>[] constructors = clazz.getConstructors();
			// Constructor constructor = clazz.getConstructors()[1];
			for (Constructor<?> constructor : constructors) {
				Object[] initargs = MethodUtil.getDefaultValue(constructor.getParameterTypes());
				constructor.newInstance(initargs);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected static boolean isValidMethodName(String methodName) {
		return (methodName.startsWith("get") || methodName.startsWith("set") || methodName.startsWith("is") || "toString".equals(methodName));
	}
}
