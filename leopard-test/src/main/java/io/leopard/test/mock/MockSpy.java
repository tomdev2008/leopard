package io.leopard.test.mock;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.core.exception.NoSuchFieldRuntimeException;
import io.leopard.test.CustomBeanUtil;
import io.leopard.test.mock.internal.Mocker;
import io.leopard.test.mock.reflect.InstanceUtil;

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
public class MockSpy {

	private static Set<String> IGNORE_STACK_CLASSNAME = new HashSet<String>();
	static {
		IGNORE_STACK_CLASSNAME.add(Mock.class.getName());
		IGNORE_STACK_CLASSNAME.add(MockSpy.class.getName());
	}
	

	/**
	 * 根据堆栈信息获取测试类.
	 * 
	 * @return
	 */
	protected static Class<?> getTestClass() {
		StackTraceElement[] stacks = new Exception().getStackTrace();
		String testClassName = null;
		for (StackTraceElement stack : stacks) {
			if (!IGNORE_STACK_CLASSNAME.contains(stack.getClassName())) {
				testClassName = stack.getClassName();
				// System.out.println("testClassName:" + testClassName);
				break;
			}
			// System.out.println(stack.getClassName());
		}

		try {
			return Class.forName(testClassName);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

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
	public static <T> void copyToTestObject(Object testObject, Class<T> clazz, Object bean) {
		Class<?> testClass = getTestClass();
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

	public static <T> T spy(Object testObject, Class<T> clazz, Mocker mocker) {

		T bean = InstanceUtil.instantiateClass(clazz);

		if (true) {
			Class<?> currentClazz = bean.getClass();
			while (true) {
				Field[] fields = currentClazz.getDeclaredFields();
				for (Field field : fields) {
					// Class<?> fieldType = field.getType();

					if (!CustomBeanUtil.isCustomBean(field.getType().getName())) {
						continue;
					}

					if (Mock.isMocked(bean, field)) {
						// 已经mock过
						continue;
					}

					Object mock = mocker.mock(field);
					if (mock == null) {
						continue;
					}

					FieldUtil.setFieldValue(bean, field, mock);
				}

				if (currentClazz.getSuperclass() == null) {
					break;
				}
				currentClazz = currentClazz.getSuperclass();
			}
		}

		Mock.mockLogs(bean);// mock 所有Log对象

		if (testObject != null) {
			MockSpy.copyToTestObject(testObject, clazz, bean); // 将mock属性复制到测试类.
			// PowerMockIgnore powerMockIgnore = testObject.getClass().getAnnotation(PowerMockIgnore.class);
			// // System.err.println("powerMockIgnore:" + powerMockIgnore);
			// if (powerMockIgnore == null) {
			// Mock.mockLogs(bean);// mock 所有Log对象
			// }
		}
		return bean;
	}
}
