package io.leopard.test.mock.proxy;

import io.leopard.burrow.refect.ClassTypeUtil;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

public class MethodHandlerAssertExceptionImpl implements MethodHandler {
	private MethodHandler methodHandler = null;
	private Class<? extends Exception> exceptionClazz = null;
	private String messagePrefix;
	private Object mock;

	public MethodHandlerAssertExceptionImpl(Object mock, Class<? extends Exception> exceptionClazz, String messagePrefix) {
		this.mock = mock;
		this.exceptionClazz = exceptionClazz;
		this.messagePrefix = messagePrefix;
	}

	public MethodHandlerAssertExceptionImpl(MethodHandler methodHandler, Class<? extends Exception> exceptionClazz, String messagePrefix) {
		this.methodHandler = methodHandler;
		this.exceptionClazz = exceptionClazz;
		this.messagePrefix = messagePrefix;
	}

	@Override
	public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
		try {
			Object result;
			if (methodHandler == null) {
				method.setAccessible(true);
				result = method.invoke(mock, args);
			}
			else {
				result = methodHandler.invoke(self, method, proceed, args);
			}
			Assert.fail("出错啦，怎么没有抛异常[" + exceptionClazz.getName() + "]？");
			return result;
		}
		catch (Exception e) {

			Throwable t = e.getCause();
			if (t == null) {
				// e.printStackTrace();
				throw e;
			}
			// System.out.println("messagePrefix:" + messagePrefix + " " + t.getMessage());
			if (t.getClass().getName().equals(exceptionClazz.getName())) {
				if (StringUtils.isEmpty(messagePrefix)) {
					return this.getDefaultMethodValue(method);
				}
				if (t.getMessage().startsWith(messagePrefix)) {
					return this.getDefaultMethodValue(method);
				}
				else {
					Assert.fail("出错啦，消息前缀不一样[" + messagePrefix + "]，怎么抛出[" + t.getMessage() + "]？");
				}
			}
			t.printStackTrace();
			Assert.fail("出错啦，正确的异常类型[" + exceptionClazz.getName() + "]，怎么抛出[" + t.getClass().getName() + "]？");
			throw t;
		}
	}

	/**
	 * 返回方法默认值.
	 * 
	 * @return
	 */
	protected Object getDefaultMethodValue(Method method) {
		Class<?> clazz = method.getReturnType();
		return getDefaultMethodValue(clazz);
	}

	protected Object getDefaultMethodValue(Class<?> clazz) {
		// Class<?> clazz = method.getReturnType();
		if (ClassTypeUtil.isInteger(clazz)) {
			return 0;
		}
		else if (ClassTypeUtil.isLong(clazz)) {
			return 0L;
		}
		else if (ClassTypeUtil.isFloat(clazz)) {
			return 0F;
		}
		else if (ClassTypeUtil.isDouble(clazz)) {
			return 0D;
		}
		else if (ClassTypeUtil.isBoolean(clazz)) {
			return false;
		}
		else {
			return null;
		}
	}

}
