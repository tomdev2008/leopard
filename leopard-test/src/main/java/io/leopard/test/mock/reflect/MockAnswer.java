package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.Json;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockAnswer {
	public static Answer<Object> getListAnswer(final String content) {
		return new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Method method = invocation.getMethod();
				// System.out.println("content:" + content);
				return MockAnswer.parseReturnValue(method, content);
			}
		};
	}

	/**
	 * 解析返回值
	 * 
	 * @param method
	 * @param content
	 * @return
	 */
	public static Object parseReturnValue(Method method, String content) {
		System.out.println("method:" + method.toGenericString());
		ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
		Type[] types = parameterizedType.getActualTypeArguments();

		if (types[0] instanceof ParameterizedType) {
			//
			return MockAnswer.parseSubType(types[0], content);
		}

		if (method.getReturnType().getName().equals(Map.class.getName())) {
			String param = content.replace("[", "");
			param = param.replace("]", "");
			return Tson.toMapObject(param, (Class<?>) types[0], (Class<?>) types[1]);
		}
		// System.out.println("types:" + types[0]);
		Class<?> clazz = (Class<?>) types[0];

		List<?> result;
		if (content.indexOf(":") == -1) {
			content = addQuotes(content, clazz);
			// System.out.println("content:" + content + " clazz:" + clazz.getName() + " returnType:" + method.getReturnType().getName());
			result = Json.toListObject(content, clazz);
		}
		else {
			String param = content.replace("[", "");
			param = param.replace("]", "");
			result = Tson.toListObject(param, clazz);
		}
		if (method.getReturnType().getName().equals(Set.class.getName())) {
			return new LinkedHashSet<Object>(result);
		}
		return result;
	}

	/**
	 * 加上引号.
	 * 
	 * @param content
	 * @return
	 */
	public static String addQuotes(String content, Class<?> clazz) {
		if (content.indexOf("'") != -1 || content.indexOf("\"") != -1) {
			// 已经有引号了
			return content;
		}
		if (!clazz.getName().equals(String.class.getName())) {
			// 不是字符串类型
			return content;
		}
		String param = content.replace("[", "");
		param = param.replace("]", "");
		String[] strs = StringUtils.split(param, ",");
		for (int i = 0; i < strs.length; i++) {
			strs[i] = "\"" + strs[i] + "\"";
		}
		//
		String result = "[" + StringUtils.join(strs, ",") + "]";
		// System.out.println("result:" + result);
		return result;
	}

	public static Object parseSubType(Type type, String content) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type rawType = parameterizedType.getRawType();
		if (rawType.toString().startsWith("interface java.util.Map$Entry")) {
			Type[] types = parameterizedType.getActualTypeArguments();
			String param = content.replace("[", "");
			param = param.replace("]", "");
			// System.out.println("types[0]:" + types[0] + " param:" + param);
			Class<?> keyType = toClass(types[0]);
			Class<?> valueType = toClass(types[1]);

			Object result = Tson.toEntryList(param, keyType, valueType);
			// System.out.println("result:" + result);
			return result;
		}
		throw new RuntimeException("未知类型:" + rawType.toString());
	}

	private static Class<?> toClass(Type type) {
		String className = type.toString().replace("class ", "");
		try {
			return Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Answer<Object> getModelAnswer(final String json) {
		return new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Method method = invocation.getMethod();
				Class<?> clazz = method.getReturnType();
				System.out.println("clazz:" + clazz.getName() + " json:" + json);

				String param = json.replace("{", "");
				param = param.replace("}", "");

				return Tson.toObject(param, clazz);
			}
		};
	}

	// public static Answer<Object> getMethodNameAnswer(String methodName) {
	// return new Answer<Object>() {
	// public Object answer(InvocationOnMock invocation) {
	// Method method = invocation.getMethod();
	// Class<?> clazz = method.getReturnType();
	// String className = clazz.getName();
	// System.out.println("method:" + method.toGenericString());
	// return null;
	// }
	// };
	// }

	public static Answer<Object> getAnswer(final Object toBeReturned) {
		return new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Method method = invocation.getMethod();
				Class<?> clazz = method.getReturnType();
				// System.out.println("clazz:" + clazz + " method:" + method.toGenericString());
				// System.out.println("toBeReturned:" + toBeReturned);
				if (toBeReturned instanceof Integer) {
					if (clazz.getName().equals(Date.class.getName())) {
						long time = (Integer) toBeReturned;
						return new Date(time);
					}
				}
				if (toBeReturned instanceof Long) {
					if (clazz.getName().equals(Date.class.getName())) {
						return new Date((Long) toBeReturned);
					}
				}
				return toBeReturned;
			}
		};
	}

	public static Answer<Object> getDefaultAnswer() {
		return new Answer<Object>() {
			@SuppressWarnings("rawtypes")
			public Object answer(InvocationOnMock invocation) {
				Method method = invocation.getMethod();
				Class<?> clazz = method.getReturnType();
				String className = clazz.getName();

				if (className.equals(int.class.getName())) {
					return 1;
				}
				if (className.equals(String.class.getName())) {
					return "string";
				}
				if (className.equals(List.class.getName())) {
					return new ArrayList();
				}
				return null;
			}
		};
	}

}
