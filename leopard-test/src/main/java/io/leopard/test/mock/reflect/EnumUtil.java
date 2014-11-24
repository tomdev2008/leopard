package io.leopard.test.mock.reflect;

import io.leopard.burrow.refect.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;



/**
 * 
 * @author 阿海
 * 
 */
public class EnumUtil {

	public static Object getId(Object obj) {
		Method method = BeanUtils.findMethodWithMinimalParameters(obj.getClass(), "toIntValue");
		if (method == null) {
			method = BeanUtils.findMethodWithMinimalParameters(obj.getClass(), "getValue");
		}
		// Object params = null;

		return MethodUtil.invoke(obj, method);
		// try {
		// Object result = method.invoke(obj);
		// return result;
		// }
		// catch (Exception e) {
		// System.err.println("methodName:" + method.getName() + " enum:" + obj
		// + " class:" + obj.getClass().getName());
		// throw new RuntimeException(e.getMessage(), e);
		// }
	}

	public static Object getFirstElement(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Object bean = null;
		for (Field field : fields) {
			if (field.getType().equals(clazz)) {
				bean = FieldUtil.getFieldValue(null, field);
				// try {
				// bean = field.get(null);
				// }
				// catch (Exception e) {
				// throw new RuntimeException(e.getMessage(), e);
				// }
				break;
			}
		}
		return bean;
	}

	// public static Object getFirstElementValue(Class<?> clazz) {
	// Object bean = getFirstElement(clazz);
	// if (bean == null) {
	// return null;
	// }
	// Method method = MethodUtil.getMethod(clazz, "getKey");
	//
	// return MethodUtil.invoke(bean, method);
	// // try {
	// // return method.invoke(bean);
	// // }
	// // catch (Exception e) {
	// // throw new RuntimeException(e.getMessage(), e);
	// // }
	// }
}
