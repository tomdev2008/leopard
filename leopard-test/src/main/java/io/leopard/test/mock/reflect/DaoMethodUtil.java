package io.leopard.test.mock.reflect;

import java.lang.reflect.Method;

public class DaoMethodUtil {

	public static Object[] getDaoMethod(Object dao, Method method, Object[] args) {
		Method daoMethod = MethodUtil.getGreaterEqualMethod(dao, method, args);
		if (daoMethod == null) {
			return null;
		}
		Class<?>[] params = daoMethod.getParameterTypes();
		Object[] daoArgs = new Object[params.length];
		for (int i = 0; i < daoArgs.length; i++) {
			if (true) {
				if (params[i].equals(Object.class)) {
					System.err.println("daoMethod:" + daoMethod.toGenericString());
					throw new RuntimeException("参数怎么回事Object类型的?");
				}
			}
			daoArgs[i] = MethodUtil.getAny(params[i]);
		}
		return new Object[] { daoMethod, daoArgs };
	}

}
