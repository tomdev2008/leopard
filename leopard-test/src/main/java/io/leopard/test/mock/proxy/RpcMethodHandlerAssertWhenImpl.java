package io.leopard.test.mock.proxy;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.test.mock.RpcMock;

import java.lang.reflect.Method;
import java.util.List;

import javassist.util.proxy.MethodHandler;

import org.junit.Assert;

public class RpcMethodHandlerAssertWhenImpl implements MethodHandler {
	// private Object service = null;
	private final Object toBeReturned;

	public RpcMethodHandlerAssertWhenImpl(Object service) {
		this(service, null);
	}

	public RpcMethodHandlerAssertWhenImpl(Object service, Object toBeReturned) {
		// System.out.println("service:" + service + " toBeReturned:" +
		// toBeReturned);
		// this.service = service;
		this.toBeReturned = toBeReturned;
		// System.err.println("toBeReturned:" + toBeReturned);
	}

	@Override
	public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
		AssertUtil.assertNotNull(self, "参数self不能为空.");
		RpcMock.doReturn(toBeReturned);
		Object result = proceed.invoke(self, args);
		Class<?> returnType = method.getReturnType();
		// System.out.println("result:" + result + " toBeReturned:" +
		// toBeReturned + " args:" + args);
		{
			if (isSimpleType(returnType)) {
				Assert.assertEquals(result, toBeReturned);
			}
			else if (returnType.equals(List.class)) {
				if (toBeReturned == null) {
					Assert.assertNull(result);
				}
				else {
					Assert.assertEquals(result, toBeReturned);
				}
			}
			else {
				throw new RuntimeException("未知返回值类型[" + returnType.getName() + "].");
			}
		}
		return result;
	}

	protected boolean isSimpleType(Class<?> returnType) {
		if (returnType.equals(boolean.class)) {
			return true;
		}
		else if (returnType.equals(int.class)) {
			return true;
		}
		else if (returnType.equals(String.class)) {
			return true;
		}
		return false;
	}
}
