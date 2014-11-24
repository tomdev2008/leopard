package io.leopard.test.mock.internal;

import io.leopard.commons.utility.ClassUtil;
import io.leopard.test.mock.reflect.MethodUtil;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

public class AssertModel {
	public static void assertModel(Class<?> clazz) {
		Object bean = ClassUtil.newInstance(clazz);
		// try {
		// bean = clazz.newInstance();
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (!isValidMethodName(methodName)) {
				continue;
			}
			Object[] args;
			if ("setReadOnly".equals(methodName)) {
				args = new Object[] { false };
			}
			else {
				args = MethodUtil.getDefaultValue(method.getParameterTypes());
			}
			try {
				// System.out.println("args:" + args);
				method.invoke(bean, args);
			}
			catch (Exception e) {
				System.err.println("method:" + method.toGenericString() + " args:" + StringUtils.join(args));
				throw new RuntimeException(e.toString(), e);
			}
		}
	}

	protected static boolean isValidMethodName(String methodName) {
		return methodName.startsWith("get") || methodName.startsWith("set") || methodName.startsWith("is") || "toString".equals(methodName);
	}
}
