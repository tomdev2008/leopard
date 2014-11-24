package io.leopard.test.mock.stubbing;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.Json;
import io.leopard.commons.utility.ClassUtil;
import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test.mock.reflect.MockAnswer;
import io.leopard.test.mock.reflect.Tson;
import io.leopard.test4j.mock.MockStatic;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;

public class ReturnStubber {

	private Object toBeReturned;

	public ReturnStubber(Object toBeReturned) {
		this.toBeReturned = toBeReturned;
	}

	private Object getReturn(Method method) {
		// System.out.println("toBeReturned:" + toBeReturned);
		if (toBeReturned instanceof String) {
			String param = (String) toBeReturned;
			if (param.startsWith("list[") || param.startsWith("[")) {
				param = param.replaceFirst("^list\\[", "[");
				param = param.replace("'", "\"");
				return MockAnswer.parseReturnValue(method, param);
			}
			else if (param.startsWith("{")) {
				param = param.replace("'", "\"");
				return null;
			}
		}
		return toBeReturned;
	}

	protected <T> List<T> toList(Class<T> clazz, String content) {

		// ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
		// Type type = parameterizedType.getRawType();
		// Type type = parameterizedType.getActualTypeArguments()[0];

		List<T> result;
		if (content.indexOf(":") == -1) {
			content = MockAnswer.addQuotes(content, clazz);
			// System.out.println("content:" + content + " clazz:" + clazz.getName() + " returnType:" + method.getReturnType().getName());
			result = Json.toListObject(content, clazz);
		}
		else {
			String param = content.replace("[", "");
			param = param.replace("]", "");

			System.out.println("clazz:" + clazz.getName());
			result = Tson.toListObject(param, clazz);
		}
		return result;
	}

	public void whenStatic(String classAndMethodName) {
		String[] strs = StringUtils.split(classAndMethodName, ".");
		String classSimpleName = strs[0];
		String methodName = strs[1];
		Method method = this.getMethod(classSimpleName, methodName);

		Object[] args = MethodUtil.getAny(method.getParameterTypes());
		Object methodResult;
		try {
			methodResult = method.invoke(null, args);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		OngoingStubbing<Object> result = PowerMockito.when(methodResult);
		Object returnValue = this.getReturn(method);
		System.out.println("returnValue:" + returnValue);
		result.thenReturn(returnValue);
	}

	protected Method getMethod(String classSimpleName, String methodName) {
		String className = MockStatic.getStaticClassName(classSimpleName);
		AssertUtil.assertNotEmpty(className, "根据[" + classSimpleName + "]获取不到完整的类名.");
		System.out.println("classSimpleName:" + classSimpleName + " className:" + className);

		Class<?> clazz = ClassUtil.forName(className);
		// try {
		// clazz = Class.forName(className);
		// }
		// catch (ClassNotFoundException e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

}
