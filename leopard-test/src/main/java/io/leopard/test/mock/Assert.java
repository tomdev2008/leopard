package io.leopard.test.mock;

import io.leopard.burrow.util.DateUtil;
import io.leopard.burrow.util.StringUtil;
import io.leopard.test.mock.internal.AssertAllModel;
import io.leopard.test.mock.internal.AssertEnum;
import io.leopard.test.mock.internal.AssertKeyModel;
import io.leopard.test.mock.internal.AssertList;
import io.leopard.test.mock.internal.AssertMap;
import io.leopard.test.mock.internal.AssertModel;
import io.leopard.test.mock.proxy.MethodHandlerAssertExceptionImpl;
import io.leopard.test.mock.proxy.MethodHandlerAssertMethodNameImpl;
import io.leopard.test.mock.proxy.MethodHandlerAssertWhenImpl;
import io.leopard.test.mock.proxy.MethodHandlerTemplateNullOrFieldImpl;
import io.leopard.test.mock.proxy.Proxy;
import io.leopard.test.mock.reflect.MethodInfo;
import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test.mock.stubbing.AbstractAssertStubber;
import io.leopard.test.mock.stubbing.AssertStubber;
import io.leopard.test.mock.stubbing.EqualsStubber;
import io.leopard.test.mock.template.MethodHandlerAddImpl;
import io.leopard.test.mock.template.MethodHandlerUpdateImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javassist.util.proxy.MethodHandler;

import org.apache.commons.lang.NotImplementedException;

/**
 * 
 * @author 阿海
 * 
 */
public class Assert extends org.junit.Assert {

	public static <T> T dao(T mock) {
		String textJson = null;
		return dao(mock, textJson);
	}

	public static <T> T dao(T mock, String textJson) {
		MethodHandler methodHandler = new MethodHandlerAddImpl<T>(mock, textJson);
		T proxy = Proxy.newProxyInstance(mock, methodHandler);
		return proxy;
	}

	public static class Dao {
		public static <T> T update(final T mock) {
			return update(mock, null);
		}

		public static <T> T update(final T mock, final String textJson) {
			return update(mock, textJson, true);
		}

		public static <T> T update(final T mock, final boolean checkGetMethod) {
			return update(mock, null, checkGetMethod);
		}

		public static <T> T update(final T mock, final String textJson, final boolean checkGetMethod) {
			MethodHandler methodHandler = new MethodHandlerUpdateImpl<T>(mock, textJson, checkGetMethod);
			T proxy = Proxy.newProxyInstance(mock, methodHandler);
			return proxy;
		}
	}

	// public static class Memcache {
	//
	// public static <T> T get(T mock) {
	// final String daoAndMethodName = "memcache.get(1)";
	// String toBeReturned = "value" + System.currentTimeMillis();
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// public static <T> T add(T mock) {
	// final String daoAndMethodName = "memcache.add(2)";
	// boolean toBeReturned = true;
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// public static <T> T put(T mock) {
	// return add(mock);
	// }
	//
	// public static <T> T remove(T mock) {
	// final String daoAndMethodName = "memcache.remove(1)";
	// boolean toBeReturned = true;
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// }

	// public static class MemoryDb {
	//
	// public static <T> T get(T mock) {
	// final String daoAndMethodName = "memoryDb.get(2)";
	// String toBeReturned = "value:" + System.currentTimeMillis();
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// public static <T> T add(T mock) {
	// boolean toBeReturned = true;
	// final String daoAndMethodName = "memoryDb.add(2)";
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// public static <T> T addObject(T mock) {
	// boolean toBeReturned = true;
	// final String daoAndMethodName = "memoryDb.addObject(2)";
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// public static <T> T put(T mock) {
	// boolean toBeReturned = true;
	// final String daoAndMethodName = "memoryDb.put(2)";
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// public static <T> T putObject(T mock) {
	// boolean toBeReturned = true;
	// final String daoAndMethodName = "memoryDb.putObject(2)";
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// public static <T> T remove(T mock) {
	// boolean toBeReturned = true;
	// final String daoAndMethodName = "memoryDb.remove(1)";
	// AssertStubber stubber = doReturn(daoAndMethodName, toBeReturned);
	// return stubber.when(mock);
	// }
	//
	// }

	/**
	 * 为实现的方法测试.
	 * 
	 * @param mock
	 * @return
	 */
	public static <T> T noimpl(final T mock) {
		MethodHandler methodHandler = new MethodHandler() {
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
				try {
					thisMethod.setAccessible(true);
					thisMethod.invoke(mock, args);
					Assert.fail("怎么没有抛出NotImplementedException异常?");
				}
				catch (InvocationTargetException e) {
					// e.printStackTrace();
					Throwable t = e.getCause();
					if (t == null) {
						Assert.fail("怎么没有抛出NotImplementedException异常?");
					}
					else if (t instanceof NotImplementedException) {
						Class<?> returnType = thisMethod.getReturnType();
						if (returnType.equals(Object.class)) {
							return null;
						}
						return MethodUtil.getDefaultValue(returnType);
					}
					else {
						Assert.fail("怎么没有抛出NotImplementedException异常?");
					}
				}
				return MethodUtil.getDefaultValue(thisMethod.getReturnType());
			}

		};
		T proxy = Proxy.newProxyInstance(mock, methodHandler);
		return proxy;
	}

	/**
	 * 验证调用次数.
	 * 
	 * <pre>
	 * Assert.verify(&quot;setTipsFlag&quot;, 0).equals(false, versionService.isShowTips(&quot;a&quot;));
	 * </pre>
	 * 
	 * @param serviceAndMethodName
	 *            方法名:methodName<br/>
	 *            类名+方法名:serviceName.methodName<br/>
	 *            类名+方法名+参数个数:serviceName.methodName(paramCount)<br/>
	 * 
	 * @param times
	 *            方法调用次数.
	 * @return
	 */
	public static EqualsStubber verify(String serviceAndMethodName, int times) {
		return new EqualsStubber(serviceAndMethodName, times);
	}

	public static <T> T verify(T mock) {
		return verify(mock, 1);
	}

	public static <T> T verify(T mock, int times) {
		return Mock.verify(mock, Mock.times(times));
	}

	/**
	 * 返回null或某个字段.
	 * 
	 * @param expected
	 * @param actual
	 */
	// public static <T, DAO> T templateNullOrField(String methodName, String
	// expected, T mock) {
	// return templateNullOrField(methodName, expected, mock, null);
	// }
	//
	// public static <T, DAO> T templateNullOrField(String methodName, String
	// returnValue, T mock, DAO mockDao) {
	// T proxy = Proxy.newProxyInstance(mock, new
	// MethodHandlerTemplateNullOrFieldImpl(mock, mockDao, methodName,
	// returnValue));
	// return proxy;
	// }

	// public static AssertStubber nullOrMock(final String serviceAndMethodName,
	// final Object returnValue) {
	// MethodInfo methodInfo = MethodUtil.parseMethodInfo(serviceAndMethodName);
	// final String fieldName = methodInfo.getFieldName();
	// // final String methodName = serviceAndMethodName.split("\\.")[1];
	//
	// return new AbstractAssertStubber() {
	// @Override
	// public <T, DAO> T when(final T mock, final DAO daoMock) {
	// MethodHandler methodHandler = new MethodHandler() {
	// @Override
	// public Object invoke(Object self, Method thisMethod, Method proceed,
	// Object[] args) throws Throwable {
	// // System.out.println("mock:" + mock);
	//
	// // Object daoMock = FieldUtils.getField(mock.getClass(), fieldName,
	// true);
	// // System.out.println("daoMock:" + daoMock.getClass().getName());
	// return null;
	// }
	// };
	// return super.invoke(mock, methodHandler);
	// }
	// };
	// }

	public static class Template {

		public static AssertStubber nullOrField(final String returnValue) {
			return new AbstractAssertStubber() {
				@Override
				public <T, DAO> T when(final T mock, final DAO daoMock) {
					final MethodHandler methodHandler = new MethodHandler() {
						@Override
						public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
							{
								T mock2 = Mock.doReturn(null).when(mock);
								Method getMethod = MethodUtil.getMethod(mock2.getClass(), "get");
								getMethod.invoke(mock2, args);
								Assert.assertNull(thisMethod.invoke(mock, args));
							}
							String fieldName = thisMethod.getName().replaceFirst("^get", "");
							fieldName = StringUtil.firstCharToLowerCase(fieldName);
							// System.out.println("fieldName:" + fieldName);
							{
								T mock2 = Mock.doReturn("{" + fieldName + ":" + returnValue + "}").when(mock);
								Method getMethod = MethodUtil.getMethod(mock2.getClass(), "get");
								getMethod.invoke(mock2, args);
								Object result = thisMethod.invoke(mock, args);
								Assert.assertEquals(returnValue, result);
								return result;
							}
						}

					};
					T proxy = Proxy.newProxyInstance(mock, methodHandler);
					return proxy;
				}
			};
		}
	}

	/**
	 * 返回null或某个字段.
	 * 
	 * @param methodName
	 * @param returnValue
	 * @return
	 */
	public static AssertStubber templateNullOrField(final String methodName, final String returnValue) {
		return new AbstractAssertStubber() {
			@Override
			public <T, DAO> T when(T mock, DAO daoMock) {
				T proxy = Proxy.newProxyInstance(mock, new MethodHandlerTemplateNullOrFieldImpl(mock, daoMock, methodName, returnValue));
				return proxy;
			}
		};
	}

	public static AssertStubber except(final Class<? extends Exception> clazz) {
		return except(clazz, null);
	}

	public static AssertStubber except(final Class<? extends Exception> clazz, final String messagePrefix) {
		return new AbstractAssertStubber() {
			@Override
			public <T, DAO> T when(T mock, DAO daoMock) {
				T proxy = Proxy.newProxyInstance(mock, new MethodHandlerAssertExceptionImpl(mock, clazz, messagePrefix));
				return proxy;
			}
		};
	}

	// TODO 这个方法有什么用？
	protected static AssertStubber exception(final Class<? extends Exception> clazz, final String messagePrefix) {
		return new AbstractAssertStubber() {
			@Override
			public <T, DAO> T when(T mock, DAO daoMock) {
				MethodHandler methodHandler = new MethodHandlerAssertWhenImpl(mock, null);
				T proxy = Proxy.newProxyInstance(mock, new MethodHandlerAssertExceptionImpl(methodHandler, clazz, messagePrefix));
				return proxy;
			}
		};
	}

	// public static <T> T exception(T mock, Class<? extends Exception> clazz) {
	// return exception(mock, clazz, null);
	// }
	//
	// public static <T> T exception(T mock, Class<? extends Exception> clazz,
	// String messagePrefix) {
	// MethodHandler methodHandler = new MethodHandlerAssertWhenImpl(mock,
	// null);
	// T proxy = Proxy.newProxyInstance(mock, new
	// MethodHandlerAssertExceptionImpl(methodHandler, clazz, messagePrefix));
	// return proxy;
	// }

	// public static <T> T except(T mock, Class<? extends Exception> clazz,
	// String messagePrefix) {
	// T proxy = Proxy.newProxyInstance(mock, new
	// MethodHandlerAssertExceptionImpl(mock, clazz, messagePrefix));
	// return proxy;
	// }

	public static void map(String content, Map<?, ?> map) {
		AssertMap.map(content, map);
	}

	// public static void list(int size, Object list) {
	// Assert.assertNotNull("list不能为null.", list);
	// List<?> list2 = (List<?>) list;
	// // System.out.println("list:" + list2.size());
	// Assert.assertEquals(size, list2.size());
	// }

	public static void assertList(String content, List<?> list) {
		list(content, list);
	}

	public static void list(String content, List<?> list) {
		AssertList.list(content, list);
	}

	/**
	 * 
	 * @param toBeReturned
	 * @return
	 */
	public static AssertStubber doReturn(final Object toBeReturned) {
		return new AbstractAssertStubber() {
			@Override
			public <T, DAO> T when(T mock, DAO daoMock) {
				T proxy = Proxy.newProxyInstance(mock, new MethodHandlerAssertWhenImpl(mock, daoMock, toBeReturned));
				return proxy;
			}
		};
	}

	public static AssertStubber doReturn(final String daoAndMethodName, final Object toBeReturned) {
		return new AbstractAssertStubber() {
			@Override
			public <T, DAO> T when(T mock, DAO daoMock) {
				Object dao = this.mock(mock);

				T proxy = Proxy.newProxyInstance(mock, new MethodHandlerAssertWhenImpl(mock, dao, toBeReturned));
				return proxy;
			}

			private <T> Object mock(T mock) {
				MethodInfo methodInfo = MethodUtil.parseMethodInfo(daoAndMethodName);
				Object dao = MethodUtil.getDaoValue(mock, methodInfo.getFieldName());
				Method daoMethod = MethodUtil.getMethod(dao.getClass(), methodInfo.getMethodName(), methodInfo.getParamCount());

				try {
					Object[] daoArgs = MethodUtil.getAny(daoMethod.getParameterTypes());
					Mock.when(daoMethod.invoke(dao, daoArgs)).thenReturn(toBeReturned);
				}
				catch (Exception e) {
					System.err.println("daoMethod:" + daoMethod.toGenericString());
					throw new RuntimeException(e.getMessage(), e);
				}
				return dao;
			}
		};
	}

	/**
	 * 自动mock和assert(适用于测试纯代理的方法).<br/>
	 * 
	 * 该方法会自动mock serviceMock第一个类型名称以Dao结尾的属性.<br/>
	 * 
	 * <pre>
	 * 例子:
	 * 1、Assert.when(service).get(1);
	 * #################适用于代码#########################
	 * public Category get(int id) {
	 * 	return this.categoryDaoCacheImpl.get(id);
	 * }
	 * 
	 * 2、Assert.when(service).get(1);
	 * Mock.verify(otherService, 1).update(id);
	 * #################适用于代码#########################
	 * public Category get(int id) {
	 * 	otherService.update(id);
	 * 	return this.categoryDaoCacheImpl.get(id);
	 * }
	 * </pre>
	 * 
	 * 
	 * @param serviceMock
	 * @return
	 */
	// public static <T> T proxy(T serviceMock) {
	// T proxy = Proxy.newProxyInstance(serviceMock, new MethodHandlerAssertWhenImpl(serviceMock, null));
	// return proxy;
	// }

	public static <T> T when(T serviceMock) {
		T proxy = Proxy.newProxyInstance(serviceMock, new MethodHandlerAssertWhenImpl(serviceMock, null));
		return proxy;
	}

	/**
	 * 自动mock和assert.
	 * 
	 * @param serviceMock
	 * @param daoMock
	 *            null:自动查找serviceMock第一个类型名称以Dao结尾的属性。非null时，可以传入任意mock对象， 如articleService,articleDaoMysqlImpl等.
	 * @return
	 */
	// public static <T, DAO> T proxy(T serviceMock, DAO daoMock) {
	// T proxy = Proxy.newProxyInstance(serviceMock, new MethodHandlerAssertWhenImpl(serviceMock, daoMock));
	// return proxy;
	// }

	public static <T, DAO> T when(T serviceMock, DAO daoMock) {
		T proxy = Proxy.newProxyInstance(serviceMock, new MethodHandlerAssertWhenImpl(serviceMock, daoMock));
		return proxy;
	}

	/**
	 * 自动mock和assert.
	 * 
	 * @param serviceMock
	 * @param daoAndMethodName
	 * @return
	 */
	public static <T> T when(T serviceMock, String daoAndMethodName) {
		T proxy = Proxy.newProxyInstance(serviceMock, new MethodHandlerAssertMethodNameImpl(serviceMock, daoAndMethodName));
		return proxy;
	}

	public static void equals(int expectedSize, Map<?, ?> map) {
		Assert.assertNotNull("参数map不能为null.", map);
		Assert.assertEquals(expectedSize, map.size());
	}

	public static void equals(int expectedSize, List<?> list) {
		Assert.assertNotNull("参数list不能为null.", list);
		Assert.assertEquals(expectedSize, list.size());
	}

	public static void equals(long expectedDate, Date actual) {
		Assert.assertNotNull("日期actual怎么会为空?", actual);
		Assert.assertEquals(expectedDate, actual.getTime());
	}

	public static void equals(String expected, Date actual) {
		Assert.assertEquals(expected, DateUtil.getTime(actual));
	}

	public static void equals(String expected, String actual) {
		Assert.assertEquals(expected, actual);
	}

	public static void equals(String expected, Object actual) {
		AssertEquals.equals(expected, actual);
	}

	public static void equals(int expected, int actual) {
		Assert.assertEquals(expected, actual);
	}

	public static void equals(int expected, Integer actual) {
		Assert.assertEquals((Integer) expected, actual);
	}

	public static void equals(int expected, long actual) {
		Assert.assertEquals(expected, actual);
	}

	public static void equals(int expected, Long actual) {
		Assert.assertEquals(expected, (long) actual);
	}

	public static void equals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	public static void assertAllModels(String packageName) {
		AssertAllModel.assertAllModels(packageName);
	}

	public static void assertEnum(Class<?> enumClass) {
		AssertEnum.assertEnum(enumClass);
	}

	public static void assertKeyModel(Class<?> clazz) {
		AssertKeyModel.assertKeyModel(clazz);
	}

	public static void assertModel(Class<?> clazz) {
		AssertModel.assertModel(clazz);
	}
}
