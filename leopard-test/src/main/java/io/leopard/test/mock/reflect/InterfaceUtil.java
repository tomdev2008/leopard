package io.leopard.test.mock.reflect;

import io.leopard.burrow.refect.FieldUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class InterfaceUtil {
	public static Set<String> getInterfaces(Class<?> clazz) {
		Set<String> set = new LinkedHashSet<String>();
		Class<?>[] classes = clazz.getInterfaces();
		for (Class<?> cls : classes) {
			if (cls.getInterfaces() != null) {
				set.addAll(getInterfaces(cls));
			}

			set.add(cls.getName());
		}
		return set;
	}

	public static Set<String> getInterfacesAndSelf(Class<?> clazz) {
		Set<String> set = getInterfaces(clazz);
		set.add(clazz.getName());
		return set;
	}

	public static Field getField(Object bean, Class<?> interfaceClass) {
		Set<String> interfaceSet = getInterfacesAndSelf(interfaceClass);
		Class<?> clazz = bean.getClass();
		while (true) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				// 为了兼容coverage插件
				if ("$jacocoData".equals(field.getName())) {
					continue;
				}
				Set<String> fieldInterfaceSet = getInterfacesAndSelf(field.getType());
				if (interfaceSet.containsAll(fieldInterfaceSet)) {
					return field;
				}
			}
			if (clazz.getSuperclass() == null) {
				return null;
			}
			clazz = clazz.getSuperclass();
		}
	}

	/**
	 * 
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static <T> T getFieldValue(Object bean, Class<T> interfaceClass) {
		Field field = getField(bean, interfaceClass);
		return (T) FieldUtil.getFieldValue(bean, field);
		// try {
		// field.setAccessible(true);
		// return (T) field.get(bean);
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
	}
}
