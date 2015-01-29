package io.leopard.test4j.mock;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.core.exception.NoSuchFieldRuntimeException;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author 阿海
 * 
 */
public class MockTransactionalSpy {

	private static Set<String> IGNORE_TYPE = new HashSet<String>();
	static {
		IGNORE_TYPE.add(String.class.getName());
		IGNORE_TYPE.add(int.class.getName());
		IGNORE_TYPE.add(Integer.class.getName());
		IGNORE_TYPE.add(float.class.getName());
		IGNORE_TYPE.add(Float.class.getName());
		IGNORE_TYPE.add(long.class.getName());
		IGNORE_TYPE.add(Long.class.getName());
		IGNORE_TYPE.add(double.class.getName());
		IGNORE_TYPE.add(Double.class.getName());
		IGNORE_TYPE.add(Date.class.getName());
		IGNORE_TYPE.add(List.class.getName());
		IGNORE_TYPE.add(Set.class.getName());
		IGNORE_TYPE.add(Map.class.getName());
	}

	protected static boolean isIgnoreType(Class<?> clazz) {
		return IGNORE_TYPE.contains(clazz.getName());
	}

	/**
	 * 将mock属性复制到测试类.
	 * 
	 * @param testObject
	 * @param clazz
	 */
	public static <T> void copyToTestObject(Object testObject, Object bean) {
		Class<?> clazz = bean.getClass().getSuperclass();

		Class<?> testClass = testObject.getClass();
		Field[] fields = testClass.getDeclaredFields();
		for (Field field : fields) {
			Class<?> fieldType = field.getType();

			if (fieldType.getName().equals(clazz.getName())) {
				// 忽略直接被测试类mock的类
				continue;
			}
			if ("$jacocoData".equals(field.getName())) {
				continue;
			}
			if ("$assertionsDisabled".equals(field.getName())) {
				continue;
			}

			if (isIgnoreType(fieldType)) {
				// 忽略基本类型.
				continue;
			}

			Object mock;
			try {
				mock = FieldUtil.getFieldValue(bean, field.getName());
			}
			catch (NoSuchFieldRuntimeException e) {
				mock = FieldUtil.getFieldValue(bean, field.getType());
			}
			// System.out.println("fieldType:" + fieldType.getName() + " mock:" + mock);
			FieldUtil.setFieldValue(testObject, field, mock);

			if (mock == null) {
				continue;
			}
			if (testObject != null) {
				field.setAccessible(true);
				try {
					field.set(testObject, mock);
				}
				catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}
}
