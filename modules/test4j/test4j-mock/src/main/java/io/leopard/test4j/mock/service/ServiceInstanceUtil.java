package io.leopard.test4j.mock.service;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.test4j.mock.dao.InstanceUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceInstanceUtil {

	protected static BeanInstance beanInstanceDaoImpl = new BeanInstanceDaoImpl();

	private static Map<String, Object> serviceCache = new ConcurrentHashMap<String, Object>();

	public static Object newInstance(Class<?> implClazz) {
		System.err.println("newInstance:" + implClazz.getName());
		String key = implClazz.getName();
		Object bean = serviceCache.get(key);
		if (bean != null) {
			return bean;
		}

		bean = InstanceUtil.instantiateClass(implClazz);
		serviceCache.put(key, bean);

		// DaoInstanceUtil.mockAllFields(bean);
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class<?> clazz = field.getType();
			if (fieldName.indexOf("Dao") != -1) {
				Object value = beanInstanceDaoImpl.instance(clazz);
				FieldUtil.setFieldValue(bean, field, value);
			}
			else if (fieldName.indexOf("Service") != -1) {
				Object value = newServiceInstance(implClazz, clazz);
				FieldUtil.setFieldValue(bean, field, value);
			}
			else {
				System.out.println("未实例化:" + field.getName());
			}

		}
		return bean;
	}

	protected static Object newServiceInstance(Class<?> implClazz, Class<?> clazz) {
		String packageName = implClazz.getPackage().getName();
		String className = clazz.getName();
		if (!className.startsWith(packageName)) {
			// 其他package的service类不做实例化.
			return null;
		}

		String implClassName = className.replace(".service.", ".service.impl.");
		implClassName += "Impl";

		Class<?> serviceImplClazz;
		try {
			serviceImplClazz = Class.forName(implClassName);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return ServiceInstanceUtil.newInstance(serviceImplClazz);

	}
}
