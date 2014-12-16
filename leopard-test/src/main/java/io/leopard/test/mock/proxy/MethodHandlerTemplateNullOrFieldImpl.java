package io.leopard.test.mock.proxy;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.test.CustomBeanUtil;
import io.leopard.test.mock.Assert;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test.mock.reflect.Tson;
import io.leopard.util.ClassTypeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import javassist.util.proxy.MethodHandler;

public class MethodHandlerTemplateNullOrFieldImpl implements MethodHandler {
	private Object service = null;
	private Object dao = null;
	private String mockMethodName = null;
	private String returnValue;

	public MethodHandlerTemplateNullOrFieldImpl(Object service, Object dao, String mockMethodName, String returnValue) {
		this.service = service;
		if (dao == null) {
			this.dao = service;
		}
		else {
			this.dao = dao;
		}
		this.mockMethodName = mockMethodName;
		this.returnValue = returnValue;
	}

	@Override
	public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
		AssertUtil.assertNotNull(self, "参数self不能为空.");
		if (true) {
			Class<?> returnType = this.mock(mockMethodName, null, args);
			Object value = method.invoke(dao, args);
			String returnTypeName = returnType.getName();
			if (returnTypeName.equals(String.class.getName())) {
				Assert.assertNull(value);
			}
			else if (returnTypeName.equals(Date.class.getName())) {
				Assert.assertNull(value);
			}
			else if (ClassTypeUtil.isInteger(returnTypeName)) {
				Assert.assertEquals(-1, value);
			}
			else if (ClassTypeUtil.isLong(returnTypeName)) {
				Assert.assertEquals(-1L, (long) (Long) value);
			}
			else if (ClassTypeUtil.isFloat(returnTypeName)) {
				Assert.assertEquals(-1f, (float) (Float) value, 0);
			}
			else if (ClassTypeUtil.isDouble(returnTypeName)) {
				Assert.assertEquals(-1D, (double) (Double) value, 0);
			}
			else {
				throw new RuntimeException("未知类型[" + returnTypeName + "].");
			}
		}

		this.mock(mockMethodName, this.returnValue, args);
		Object value = method.invoke(service, args);
		Assert.assertNotNull(value);
		Object expected = Tson.getValue(this.returnValue, value.getClass());
		Assert.assertEquals(expected, value);
		return value;
	}

	protected Class<?> mock(String methodName, String returnValue, Object[] args) {
		Method method = MethodUtil.getMethod(dao, methodName, args);
		Class<?> returnType = method.getReturnType();

		// Class<?>[] parameterTypes = MethodUtil.getTypes(args);
		if (returnValue == null) {
			if (CustomBeanUtil.isCustomBean(returnType.getName())) {
				returnType = this.getRealReturnType(returnType);
			}
		}
		Object proxy = Mock.doReturn(returnValue).when(dao);
		Object[] anyArgs = MethodUtil.getAny(method.getParameterTypes());
		try {
			System.out.println("method:" + method);
			method.invoke(proxy, anyArgs);
		}
		catch (Exception e) {
			System.err.println("method:" + method.toGenericString() + " returnValue:" + returnValue);
			throw new RuntimeException(e.getMessage(), e);
		}
		return returnType;
	}

	protected Class<?> getRealReturnType(Class<?> returnType) {
		String fieldName = Tson.getFieldName(this.returnValue);
		try {
			Field field = returnType.getDeclaredField(fieldName);
			return field.getType();
		}
		catch (Exception e) {
			System.err.println("returnType:" + returnType.getName() + " fieldName:" + fieldName);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	// private Object getDefaultValue(Class<?> returnType) {
	//
	// if (returnType.getName().equals(String.class.getName())) {
	// return null;
	// }
	//
	// if (returnType.getName().equals(int.class.getName()) ||
	// returnType.getName().equals(Integer.class.getName())) {
	// return -1;
	// }
	// if (returnType.getName().equals(long.class.getName()) ||
	// returnType.getName().equals(Long.class.getName())) {
	// return -1;
	// }
	// if (returnType.getName().equals(float.class.getName()) ||
	// returnType.getName().equals(Float.class.getName())) {
	// return -1;
	// }
	// if (returnType.getName().equals(double.class.getName()) ||
	// returnType.getName().equals(Double.class.getName())) {
	// return -1;
	// }
	// if (returnType.getName().equals(Date.class.getName())) {
	// return null;
	// }
	//
	// throw new RuntimeException("未知类型[" + returnType.getName() + "].");
	// }
}
