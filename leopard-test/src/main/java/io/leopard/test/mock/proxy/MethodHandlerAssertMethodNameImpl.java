package io.leopard.test.mock.proxy;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.commons.utility.BeanUtil;
import io.leopard.test.mock.Assert;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.reflect.MethodUtil;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

import org.apache.commons.lang.StringUtils;

public class MethodHandlerAssertMethodNameImpl implements MethodHandler {
	private Object service = null;
	protected Object dao = null;
	protected String methodName = null;

	public MethodHandlerAssertMethodNameImpl(Object service, String daoAndMethodName) {
		this.service = service;
		{
			String[] strs = StringUtils.split(daoAndMethodName, ".");
			if (strs.length == 1) {
				methodName = strs[0];
			}
			else if (strs.length == 2) {
				this.dao = BeanUtil.getFieldValue(service, strs[0]);
				methodName = strs[1];
			}
			else {
				throw new IllegalArgumentException("非法方法名参数[" + daoAndMethodName + "].");
			}
		}

	}

	@Override
	public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
		AssertUtil.assertNotNull(self, "参数self不能为空.");
		Method daoMethod = MethodUtil.getMethod(dao, methodName, args);
		AssertUtil.assertNotNull(daoMethod, "获取dao的方法[" + methodName + "]出错.");
		Object daoValue = MethodUtil.getDefaultValue(daoMethod.getReturnType());
		Mock.when(daoMethod.invoke(dao, args)).thenReturn(daoValue);

		method.setAccessible(true);
		// ///////////
		Object serviceValue = method.invoke(service, args);

		Assert.assertEquals(daoValue, serviceValue);
		return daoValue;
	}
}
