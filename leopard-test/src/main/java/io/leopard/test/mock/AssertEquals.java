package io.leopard.test.mock;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.refect.FieldUtil;
import io.leopard.test.mock.reflect.Tson;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class AssertEquals {
	public static void equals(String expected, Object actual) {
		if (expected != null) {
			if (expected.startsWith("[") || expected.startsWith("{")) {
				equalsByTson(expected, actual);
			}
			else {
				Assert.assertEquals(expected, actual);
			}
		}
		else {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void equalsByTson(String expected, Object actual) {
		AssertUtil.assertNotNull(actual, "参数actual不能为null.");
		if (expected.startsWith("[")) {
			// expected = expected.replaceFirst("^list\\[", "[");
			// expected = expected.replace("'", "\"");
			// throw new NotImplementedException("还不支持List验证.");
			equalsByTsonList(expected, (List<?>) actual);
		}
		else if (expected.startsWith("{")) {
			equalsByTsonModel(expected, actual);
		}
		else {
			throw new IllegalArgumentException("非法Textson[" + expected + "].");
		}
	}

	protected static void equalsByTsonList(String expected, List<?> actual) {
		Object element = actual.get(0);
		Class<?> clazz = element.getClass();

		System.out.println("clazz:" + clazz.getName());

		String param = expected.replace("[", "");
		param = param.replace("]", "");

		String[] params = param.split(";");
		for (int i = 0; i < params.length; i++) {
			equalsByTsonModel(params[i], actual.get(i));
		}

		// ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
		// Type[] types = parameterizedType.getActualTypeArguments();
		//
		// String msg = types[1].toString();
		//
		// System.err.println("clazz:" + msg);
	}

	protected static void equalsByTsonModel(String expected, Object actual) {
		expected = expected.replace("'", "\"");
		String param = expected.replace("{", "");
		param = param.replace("}", "");
		Object value = Tson.toObject(param, actual.getClass());
		Set<String> nameSet = Tson.getNameSet(param);
		for (String fieldName : nameSet) {
			Object value1 = FieldUtil.getFieldValue(value, fieldName);
			Object value2 = FieldUtil.getFieldValue(actual, fieldName);

			if (value1 instanceof Date) {
				Date date1 = ((Date) value1);
				Date date2 = ((Date) value2);
				Assert.assertEquals("时间字段[" + fieldName + "]不匹配", date1.getTime(), date2.getTime());
			}
			else {
				Assert.assertEquals("字段[" + fieldName + "]不匹配", value1, value2);
			}
		}
	}
}
