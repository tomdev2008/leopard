package io.leopard.test.mock.stubbing;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.proxy.Proxy;
import io.leopard.test.mock.reflect.MethodInfo;
import io.leopard.test.mock.reflect.MethodUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javassist.util.proxy.MethodHandler;

import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.Assert;

public class EqualsStubber {

	private final String serviceAndMethodName;
	private final int times;

	// private Object value;

	public EqualsStubber(String serviceAndMethodName, int times) {
		this.serviceAndMethodName = serviceAndMethodName;
		this.times = times;
	}

	public void equals(int expectedSize, List<?> list) {
		Assert.assertEquals(expectedSize, list.size());
		this.verify();
	}

	public void equals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
		this.verify();
	}

	public void verify() {
		Object mock = Mock.getCurrentMock();
		this.verify(mock);
	}

	public void verify(Object mock) {
		// Mock.verify(daoMock, times).xxx();
		Object daoMock = getDaoMock(mock);
		// System.out.println("daoMock:" + daoMock.getClass().getName());
		Method daoMethod = getMethod(daoMock);
		// System.out.println("daoMethod:" + daoMethod.toGenericString());
		Object verifyMock = Mock.verify(daoMock, times);
		// System.out.println("serviceAndMethodName:" + serviceAndMethodName + " times:" + times);
		daoMethod.setAccessible(true);
		try {
			daoMethod.invoke(verifyMock, MethodUtil.getAny(daoMethod.getParameterTypes()));
		}
		catch (Exception e) {
			System.err.println("daoMethod:" + daoMethod.toGenericString());
			throw new RuntimeException(e.toString(), e);
		}
		Mock.reset(daoMock);
	}

	// public EqualsStubber equal(Object value) {
	// this.value = value;
	// return this;
	// }

	protected Method getMethod(Object daoMock) {
		MethodInfo methodInfo = MethodUtil.parseMethodInfo(serviceAndMethodName);
		// Json.print(methodInfo, "methodInfo");
		return MethodUtil.getMethod(daoMock.getClass(), methodInfo.getMethodName(), methodInfo.getParamCount());
	}

	protected Object getDaoMock(Object mock) {
		MethodInfo methodInfo = MethodUtil.parseMethodInfo(serviceAndMethodName);
		// Json.print(methodInfo, "methodInfo");
		if (methodInfo.getFieldName() == null) {
			return mock;
		}
		Field field = FieldUtils.getField(mock.getClass(), methodInfo.getFieldName(), true);
		// try {
		// return field.get(mock);
		// } catch (Exception e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
		return FieldUtil.getFieldValue(mock, field);
	}

	public <T> T when(final T mock) {
		MethodHandler methodHandler = new MethodHandler() {
			@Override
			public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
				Object result = method.invoke(mock, args);
				verify(mock);
				return result;
			}
		};
		T proxy = Proxy.newProxyInstance(mock, methodHandler);
		return proxy;
	}
}
