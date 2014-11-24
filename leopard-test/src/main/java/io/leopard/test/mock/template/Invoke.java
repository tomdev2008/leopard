package io.leopard.test.mock.template;

import io.leopard.commons.utility.ObjectUtil;
import io.leopard.data4j.cache.api.IDelete;
import io.leopard.data4j.cache.api.IGet;
import io.leopard.reflect.CtClassUtil;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test.mock.reflect.ModelInstanceUtil;
import io.leopard.test.mock.reflect.Tson;
import io.leopard.util.ClassTypeUtil;
import io.leopard.util.CustomBeanUtil;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

public class Invoke {
	public static boolean hasGet(Object dao) {
		Method getMethod = MethodUtil.getMethod(dao.getClass(), "get");
		// return getMethod != null;
		return ObjectUtil.isNotNull(getMethod);
	}

	public static <BEAN, KEYTYPE> BEAN get(IGet<BEAN, KEYTYPE> dao, KEYTYPE key) {
		return dao.get(key);
	}

	public static <BEAN, KEYTYPE> boolean delete(IDelete<BEAN, KEYTYPE> dao, KEYTYPE key, String username, Date lmodify) {
		boolean success = dao.delete(key, username, lmodify);
		return success;
	}

	// public static <BEAN, KEYTYPE> boolean remove(IRemove<BEAN, KEYTYPE> dao,
	// KEYTYPE key) {
	// boolean success = dao.remove(key);
	// return success;
	// }

	// public static <BEAN> boolean add(IAdd<BEAN> dao, String textJson) {
	// // dao.getClass().getMethod("add", parameterTypes)
	// Class<?>[] classes = dao.getClass().getSuperclass().getInterfaces();
	// for (Class<?> clazz : classes) {
	// System.out.println("inter:" + clazz.getName());
	// }
	// Method addMethod;
	// try {
	// addMethod = dao.getClass().getMethod("add", Object.class);
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// Type type = addMethod.getParameterTypes()[0];
	//
	// System.out.println("type.getClass():" + addMethod.toGenericString());
	// System.out.println("type.getClass():" + type.getClass());
	// @SuppressWarnings("unchecked")
	// Class<BEAN> modelType = (Class<BEAN>) type.getClass();
	//
	// BEAN bean = Mock.newInstance(textJson, modelType);
	//
	// return dao.add(bean);
	// }

	public static boolean addXxx(Object dao, String textJson) {
		Class<?> clazz = dao.getClass();
		Method addMethod = MethodUtil.getMethod(clazz, "add");
		if (addMethod == null) {
			throw new RuntimeException("add方法不存在.");
		}
		if (addMethod.getParameterTypes().length < 1) {
			throw new RuntimeException("add方法没有参数?");
		}
		Class<?> returnType = addMethod.getParameterTypes()[0];
		if (!CustomBeanUtil.isCustomBean(returnType)) {
			// throw new RuntimeException("add方法第一个参数不是普通的model?");
			// ModelInstanceUtil
			Map<String, String> map = Tson.parseMap(textJson);
			String[] names = CtClassUtil.getParameterNames(clazz.getSuperclass(), addMethod);
			Class<?>[] params = addMethod.getParameterTypes();
			Object[] args = ModelInstanceUtil.getDefaultValue(params);
			for (int i = 0; i < names.length; i++) {
				if (map.containsKey(names[i])) {
					String value = map.get(names[i]);
					args[i] = getDefaultValue(params[i], value);
				}
			}
			// System.out.println("addMethod:" + addMethod.toGenericString());
			// System.out.println("args:" + StringUtils.join(args, ","));
			return (Boolean) MethodUtil.invoke(dao, addMethod, args);
		}
		Object bean = Mock.newInstance(textJson, returnType);
		return (Boolean) MethodUtil.invoke(dao, addMethod, bean);
	}

	protected static Object getDefaultValue(Class<?> type, String value) {
		if (ClassTypeUtil.isInteger(type)) {
			return Integer.parseInt(value);
		}
		else if (ClassTypeUtil.isLong(type)) {
			return Long.parseLong(value);
		}
		else if (type.equals(String.class)) {
			return value;
		}
		else {
			throw new RuntimeException("未知类型[" + type.getName() + "].");
		}
	}

	public static boolean update(Object dao, String textJson) {
		Class<?> clazz = dao.getClass();
		Method updateMethod = MethodUtil.getMethod(clazz, "update");
		if (updateMethod.getParameterTypes().length < 1) {
			throw new RuntimeException("update方法没有参数?");
		}
		Class<?> returnType = updateMethod.getParameterTypes()[0];
		if (!CustomBeanUtil.isCustomBean(returnType)) {
			throw new RuntimeException("update方法第一个参数不是普通的model?");
		}
		Object bean = Mock.newInstance(textJson, returnType);
		return (Boolean) MethodUtil.invoke(dao, updateMethod, bean);
	}
}
