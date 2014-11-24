package io.leopard.test.mock.proxy;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.test.mock.Assert;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.reflect.DaoMethodUtil;
import io.leopard.test.mock.reflect.MethodUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

public class MethodHandlerAssertWhenImpl implements MethodHandler {
	private Object service = null;
	private Object dao = null;
	protected Object toBeReturned;

	public MethodHandlerAssertWhenImpl(Object service, Object dao) {
		this(service, dao, null);
	}

	public MethodHandlerAssertWhenImpl(Object service, Object dao, Object toBeReturned) {
		this.service = service;
		this.toBeReturned = toBeReturned;
		// System.out.println("this.toBeReturned:"+toBeReturned);
		if (dao == null) {
			this.dao = MethodUtil.getDaoValue(service);
			// System.out.println("this.dao:" + this.dao);
		}
		else {
			this.dao = dao;
		}

		if (this.dao == null) {
			String serviceName = service.getClass().getName();
			serviceName = serviceName.replaceAll("\\$\\$.+", "");
			String message = "在service[" + serviceName + "]中找不到对应的dao";
			throw new RuntimeException(message);
		}

		// System.out.println("service:" + service + " dao:" + this.dao);

	}

	@Override
	public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
		AssertUtil.assertNotNull(self, "参数self不能为空.");
		Object daoValue = this.invoke(method, args, this.toBeReturned);
		if (this.toBeReturned == null) {
			if (method.getReturnType().equals(boolean.class)) {
				this.invoke(method, args, false);
			}
		}
		return daoValue;
	}

	protected Object invoke(Method method, Object[] args, Object toBeReturned) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Object daoValue;
		// System.out.println("toBeReturned:" + toBeReturned);
		if (toBeReturned == null) {
			daoValue = MethodUtil.getDefaultValue(method.getReturnType());
			// System.out.println("daoValue:" + daoValue);
		}
		else {
			daoValue = toBeReturned;

		}

		when(method, args, daoValue);

		// Object[] daoMethodInfos = DaoMethodUtil.getDaoMethod(dao, method,
		// args);
		// if (daoMethodInfos != null) {
		// Method daoMethod = (Method) daoMethodInfos[0];
		// Object[] daoArgs = (Object[]) daoMethodInfos[1];
		// Mock.when(daoMethod.invoke(dao, daoArgs)).thenReturn(daoValue);
		// }

		method.setAccessible(true);
		// ///////////
		Object serviceValue = method.invoke(service, args);
		Assert.assertEquals("service和dao的返回值不一致?", daoValue, serviceValue);
		return daoValue;
	}

	protected void when(Method method, Object[] args, Object daoValue) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		System.out.println("dao:" + dao);
		System.out.println("method:" + method);
		Object[] daoMethodInfos = DaoMethodUtil.getDaoMethod(dao, method, args);
		System.out.println("daoMethodInfos:" + daoMethodInfos);
		if (daoMethodInfos != null) {
			Method daoMethod = (Method) daoMethodInfos[0];
			Object[] daoArgs = (Object[]) daoMethodInfos[1];
			Mock.when(daoMethod.invoke(dao, daoArgs)).thenReturn(daoValue);
		}
	}
}
