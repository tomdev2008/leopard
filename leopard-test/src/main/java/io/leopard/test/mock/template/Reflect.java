package io.leopard.test.mock.template;

import io.leopard.test.mock.Mock;
import io.leopard.test.mock.reflect.Tson;

import java.lang.reflect.Method;

public class Reflect {

	public static void printDeclaredMethods(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.toGenericString());
		}
	}

	// /**
	// * 是否默认删除方法.
	// *
	// * @param dao
	// * @param method
	// * @return
	// */
	// public static boolean isDefaultDeleteMethod(Object dao, Method method) {
	// Class<?>[] params = method.getParameterTypes();
	// if (params.length != 3) {
	// return false;
	// }
	// if (!params[1].equals(String.class)) {
	// return false;
	// }
	// if (!params[2].equals(Date.class)) {
	// return false;
	// }
	// return true;
	// }

	public static Class<?> getRealClass(Object dao) {
		Class<?> clazz = dao.getClass();
		if (clazz.getName().indexOf("$$EnhancerByMockitoWithCGLIB") != -1) {
			return clazz.getSuperclass();
		}
		return clazz;
	}

	public static <KEYTYPE> Class<?> getReturnType(Object dao, KEYTYPE key) {
		Class<?> clazz = getRealClass(dao);
		try {
			Method getMethod = clazz.getDeclaredMethod("get", key.getClass());
			return getMethod.getReturnType();
		}
		catch (NoSuchMethodException e) {
			printDeclaredMethods(clazz);
			throw new RuntimeException("get方法不存在[" + clazz.getName() + "." + key.getClass().getName() + "]", e);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	// public static <KEYTYPE> Class<?> getReturnType2(Object dao, KEYTYPE key1,
	// KEYTYPE key2) {
	// try {
	// Method getMethod = dao.getClass().getMethod("get", key1.getClass(),
	// key2.getClass());
	// return getMethod.getReturnType();
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// }
	//
	// public static <KEYTYPE> Class<?> getReturnType3(Object dao, KEYTYPE key1,
	// KEYTYPE key2, KEYTYPE key3) {
	// try {
	// Method getMethod = dao.getClass().getMethod("get", key1.getClass(),
	// key2.getClass(), key3.getClass());
	// return getMethod.getReturnType();
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// }

	public static Object makeBean(Object dao, Method method, Object[] args, String textJson) {
		Class<?> modelType = Reflect.getReturnType(dao, args[0]);
		return Reflect.makeBean(dao, method, args, textJson, modelType);
	}

	public static Object makeBean(Object dao, Method method, Object[] args, String textJson, Class<?> modelType) {
		Object bean;
		{
			String[] names = CtClassUtil.getParameterNames(method);
			String textJson2 = Tson.toTextJson(textJson, names, args);
			bean = Mock.newInstance(textJson2, modelType);
		}
		return bean;
	}

}
