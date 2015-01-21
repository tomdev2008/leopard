package io.leopard.test.mock;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.burrow.util.ListUtil;
import io.leopard.burrow.util.SetUtil;
import io.leopard.test.mock.internal.Mocker;
import io.leopard.test.mock.proxy.MockMethodUtil;
import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test.mock.reflect.MockAnswer;
import io.leopard.test.mock.reflect.ModelInstanceUtil;
import io.leopard.test.mock.reflect.Tson;
import io.leopard.test.mock.stubbing.ReturnStubber;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.mockito.stubbing.Stubber;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.BeanUtils;

/**
 * 
 * @author 阿海
 * 
 */
public class Mock extends Mockito {

	private static int spyCount = 0;

	private static final ThreadLocal<Object> CURRENT_MOCK = new ThreadLocal<Object>();

	public static Object getCurrentMock() {
		return CURRENT_MOCK.get();
	}

	public static int getSpyCount() {
		return spyCount;
	}

	public static <T> T dao(Object testObject, Class<T> clazz) {
		return null;
	}

	// public static void rollback() {
	//
	// }

	public static <T> T newInstance(Class<T> clazz) {
		return ModelInstanceUtil.getNewInstance(clazz);
	}

	public static <T> List<T> newList(String tson, Class<T> valueType) {
		List<T> list = Tson.toListObject(tson, valueType);
		return list;
	}

	public static <T> T newInstance(String content, Class<T> clazz) {
		T bean = ModelInstanceUtil.getNewInstance(clazz);
		// Json.print(bean, "bean");
		Map<String, String> map = Tson.parseMap(content);
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String fieldName = entry.getKey();
			String value = entry.getValue();
			Field field = FieldUtils.getField(clazz, fieldName, true);
			if (field == null) {
				continue;
				// throw new NullPointerException("对象[" + clazz.getSimpleName()
				// + "]字段[" + fieldName + "]不存在.");
			}
			Class<?> type = field.getType();
			//
			Object result = Tson.parse(value, type);
			// System.out.println("fieldName:" + fieldName + " value:" +
			// result);
			try {
				field.set(bean, result);
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		return bean;
	}

	// public static <T> T service(Object testObject, final Class<T> clazz) {
	// if (testObject != null) {// 父类检查
	// Class<?> superClass = testObject.getClass().getSuperclass();
	// if (superClass == null ||
	// !superClass.getName().equals(MockTransactionalTests.class.getName())) {
	// String currentClassName = testObject.getClass().getSimpleName();
	// throw new RuntimeException("当前类[" + currentClassName +
	// "]没有继承MockTransactionalTests");
	// }
	// }
	//
	// // String beanName = StringUtil.firstCharToLowerCase(clazz.getName());
	// @SuppressWarnings("unchecked")
	// T mock = (T) ServiceInstanceUtil.newInstance(clazz);
	// mock = Mockito.spy(mock);
	// MockSpy.copyToTestObject(testObject, clazz, mock); // 将mock属性复制到测试类.
	//
	// if (true) {
	// // @PostConstruct
	// Method method = findPostConstructMethod(clazz);
	// if (method != null) {
	// // method.invoke(mock, null);
	// MethodUtil.invoke(mock, method, new Object[] {});
	// }
	// }
	// return mock;
	// }

	protected static Method findPostConstructMethod(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			PostConstruct post = method.getAnnotation(PostConstruct.class);
			if (post != null) {
				return method;
			}
		}
		return null;
	}

	/**
	 * 验证静态方法调用.
	 * 
	 * @param clazz
	 * @param methodName
	 */
	public static void verifyStatic(Class<?> clazz, String methodName) {
		Mock.verifyStatic(clazz, methodName, 1);
	}

	public static void verifyStatic(Class<?> clazz, String methodName, int times) {
		PowerMockito.verifyStatic(Mock.times(times));

		Method method = BeanUtils.findMethodWithMinimalParameters(clazz, methodName);
		Class<?>[] params = method.getParameterTypes();
		Object[] args = new Object[params.length];
		for (int i = 0; i < args.length; i++) {
			args[i] = MethodUtil.getAny(params[i]);
		}

		MethodUtil.invoke(null, method, args);
		// try {
		// method.invoke(null, args);
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.toString(), e);
		// }
	}

	public static void verifyStatic(String classAndMethodName) {
		// PowerMockito.verifyStatic();
		// XmlUtil.getYtalkcounttipXml(Mock.anyInt(), Mock.anyInt(),
		// Mock.anyInt(), Mock.anyInt(), Mock.anyInt());
	}

	public static Date anyDate() {
		return Matchers.any(Date.class);
	}

	public static File anyFile() {
		return Matchers.any(File.class);
	}

	public static <T> List<T> anyList(Class<T> clazz) {
		return Matchers.anyListOf(clazz);
	}

	/**
	 * 对原生的verify进行封装(支持输入数字类型的times参数).
	 * 
	 * @param mock
	 * @param times
	 * @return
	 */
	public static <T> T verify(T mock, int times) {
		return Mock.verify(mock, Mock.times(times));
	}

	// /**
	// * mock某个熟悉.(如mock ArticelService中的ArticleDao)
	// *
	// * @param bean
	// * @param interfaceClass
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// protected static <T> T mock(Object bean, Class<T> interfaceClass, String
	// fieldName) {
	// Field field;
	// try {
	// field = BeanUtil.getField(bean, fieldName);
	// }
	// catch (Exception e) {
	// System.err.println("fieldName:" + fieldName);
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// field.setAccessible(true);
	// Class<T> clazz = (Class<T>) field.getType();
	// T mock = mock(clazz);
	//
	// FieldUtil.setFieldValue(bean, field, mock);
	//
	// // try {
	// // field.set(bean, mock);
	// // }
	// // catch (Exception e) {
	// // throw new RuntimeException(e.getMessage(), e);
	// // }
	// return mock;
	// }

	/**
	 * 是否已经mock过？
	 * 
	 * @param bean
	 * @param field
	 * @return
	 */
	protected static boolean isMocked(Object bean, Field field) {
		// field.setAccessible(true);
		// Object value;
		// try {
		// value = field.get(bean);
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }

		Object value = FieldUtil.getFieldValue(bean, field);
		return (value != null && new MockUtil().isMock(value));
	}

	/**
	 * mock所有Log
	 * 
	 * @param bean
	 */
	protected static void mockLogs(Object bean) {
		Class<?> clazz = bean.getClass();
		while (true) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Class<?> interfaceClass = field.getType();
				if (!interfaceClass.getName().equals(Log.class.getName())) {
					// 不是Log类型
					continue;
				}
				field.setAccessible(true);
				if (Mock.isMocked(bean, field)) {
					// 已经mock过
					continue;
				}

				if (Modifier.isFinal(field.getModifiers())) {
					// 修改final类型的字段.
					try {
						Field modifiersField = Field.class.getDeclaredField("modifiers");
						modifiersField.setAccessible(true);
						modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				// System.out.println("interfaceClass:" + interfaceClass);
				Object mock = mock(interfaceClass);
				try {
					field.set(bean, mock);
				}
				catch (Exception e) {
					System.err.println("fieldName:" + field.getName() + " message:" + e.getMessage());
					// throw new RuntimeException(e.getMessage(), e);
				}
			}
			if (clazz.getSuperclass() == null) {
				break;
			}
			clazz = clazz.getSuperclass();
		}
	}

	/**
	 * 字符串转List<Integer>.
	 * 
	 * @param content
	 *            格式:"1,2,3"
	 * @return the list
	 */
	public static List<Integer> intList(String content) {
		return ListUtil.makeIntList(content);
	}

	/**
	 * 字符串转Set<Integer>.
	 * 
	 * @param content
	 *            格式:"1,2,3"
	 * @return the list
	 */
	public static Set<Integer> intSet(String content) {
		return SetUtil.makeIntSet(content);
	}

	public static Set<Long> longSet(String content) {
		return SetUtil.makeLongSet(content);
	}

	/**
	 * 字符串转List<String>.
	 * 
	 * @param content
	 *            格式:"a,b,c"
	 * @return the list
	 */
	public static List<String> strList(String content) {
		return ListUtil.makeList(content);
	}

	/**
	 * 字符串转Set<String>.
	 * 
	 * @param content
	 *            格式:"a,b,c"
	 * @return the list
	 */
	public static Set<String> strSet(String content) {
		return SetUtil.makeSet(content);
	}

	protected static <T> T spy(Class<T> clazz) {
		return null;
	}

	/**
	 * Spy.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param testObject
	 *            the test object
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public static <T> T spy(Object testObject, Class<T> clazz) {
		Mocker mocker = new Mocker() {
			@Override
			public Object mock(Field field) {
				field.setAccessible(true);
				return Mock.mock(field.getType());
			}
		};

		return Mock.spy(testObject, clazz, mocker);
	}

	public static <T> T spy(Object testObject, Class<T> clazz, Mocker mocker) {
		spyCount++;

		T bean = MockSpy.spy(testObject, clazz, mocker);

		T spy = Mock.spy(bean);
		CURRENT_MOCK.set(spy);
		return spy;
	}

	public static <T> T back(T mock) {
		return Mock.doReturn().when(mock);
	}

	/**
	 * 异常捕获测试.
	 * 
	 * @param mock
	 * @param methodName
	 * @param toBeThrown
	 * @return
	 */
	public static <T> Stubber except(T mock, String methodName, Throwable toBeThrown) {
		Stubber stubber = Mock.doThrow(toBeThrown);
		MockMethodUtil.invokeMethod(stubber, mock, methodName);
		return stubber;
	}

	/**
	 * 根据方法名mock返回默认值(doReturn方法的封装，参数使用any).
	 * 
	 * @param mock
	 * @param methodName
	 * @return
	 */
	public static <T> Stubber goback(T mock, String methodName) {
		Stubber stubber = Mock.doReturn();
		MockMethodUtil.invokeMethod(stubber, mock, methodName);
		return stubber;
	}

	/**
	 * 根据方法名mock返回指定值(doReturn方法的封装，参数使用any).
	 * 
	 * @param mock
	 * @param methodName
	 * @return
	 */
	public static <T> Stubber goback(T mock, String methodName, Object value) {
		Stubber stubber = Mock.doReturn(value);
		// T proxy = stubber.when(mock);
		MockMethodUtil.invokeMethod(stubber, mock, methodName);
		return stubber;
	}

	/**
	 * 根据方法名mock返回指定值(doReturn方法的封装，参数使用any).
	 * 
	 * @param mock
	 * @param methodName
	 * @return
	 */
	public static void goback(Class<?> clazz, String methodName, Object value) {
		List<Method> methodList = MethodUtil.listMethod(clazz, methodName);
		// Method[] methods = clazz.getMethods();
		for (Method method : methodList) {
			// if (method.getName().equals(methodName)) {
			Object[] args = MethodUtil.getAny(method.getParameterTypes());
			// try {
			// Object result = method.invoke(null, args);
			// }
			// catch (Exception e) {
			// throw new RuntimeException(e.toString(), e);
			// }
			Object result = MethodUtil.invoke(null, method, args);
			PowerMockito.when(result).thenReturn(value);
			// }
		}

		// Method method = BeanUtils.findMethodWithMinimalParameters(clazz,
		// methodName);
		// Class<?>[] params = method.getParameterTypes();
		// Object[] args = new Object[params.length];
		// for (int i = 0; i < args.length; i++) {
		// args[i] = MethodUtil.getAny(params[i]);
		// }
		// Object result = null;
		// try {
		// System.out.println("method:" + method.toGenericString());
		// result = method.invoke(null, args);
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.toString(), e);
		// }
		//
		//
		// if (value == null) {
		// value = MethodUtil.getDefaultValue(method.getReturnType());
		// }
		// PowerMockito.when(result).thenReturn(value);
		// PowerMockito.doReturn(value);
	}

	/**
	 * 自动设置被mock方法的返回值.
	 * 
	 * @return 根据被mock方法返回值类型判断,String:"string",Number:1,List:new ArrayList()
	 */
	public static Stubber doReturn() {
		return Mockito.doAnswer(MockAnswer.getDefaultAnswer());
	}

	/**
	 * mock方法的返回值(支持TextJson)
	 * 
	 * <pre>
	 * 例子:
	 * 1、Mock.doReturn("[1,2,3]").when(service).listArticleId(username);
	 * 2、Mock.doReturn("{title:标题}").when(service).getArticle(username);
	 * 
	 * 3、String textJson = "[title:标题1;title:标题2,commentCount:2]";
	 *   Mock.doReturn(textJson).when(service).getArticle(username);
	 * 
	 * </pre>
	 * 
	 * @param toBeReturned
	 *            格式:
	 * 
	 *            <pre>
	 * 1、支持普通对象
	 * 2、List<Integer>:"[1,2,3]"
	 * 3、List<String>:"[a,b,c]"
	 * 4、List<Game>:"[gameId:ddt;gameId:sxd,userCount:1]"
	 * </pre>
	 * 
	 * @return
	 */
	public static Stubber doReturn(Object toBeReturned) {
		Stubber stubber;
		if (toBeReturned instanceof String) {
			String param = (String) toBeReturned;
			if (param.startsWith("list[") || param.startsWith("[")) {
				param = param.replaceFirst("^list\\[", "[");
				param = param.replace("'", "\"");
				// System.out.println("param:" + param);
				stubber = Mockito.doAnswer(MockAnswer.getListAnswer(param));
			}
			else if (param.startsWith("{")) {
				param = param.replace("'", "\"");
				stubber = Mockito.doAnswer(MockAnswer.getModelAnswer(param));
			}
			else {
				stubber = Mockito.doAnswer(MockAnswer.getAnswer(toBeReturned));
			}
		}
		else {
			stubber = Mockito.doAnswer(MockAnswer.getAnswer(toBeReturned));
		}
		return stubber;
	}

	public static ReturnStubber doStaticReturn(Object toBeReturned) {
		return new ReturnStubber(toBeReturned);
	}

	//
	// public static ReturnStubber doProxyReturn(Object toBeReturned) {
	// ReturnStubber stubber = new ReturnStubber(toBeReturned);
	// MethodHandler methodHandler = new MethodHandler() {
	// @Override
	// public Object invoke(Object self, Method thisMethod, Method proceed,
	// Object[] args) throws Throwable {
	// System.out.println("proceed:" + proceed);
	// return null;
	// }
	// };
	// ReturnStubber proxy = Proxy.newProxyInstance(stubber, methodHandler);
	// return proxy;
	// }

	// @SuppressWarnings("unchecked")
	// public static <T> T proxy(Object bean, Class<T> interfaceClass) {
	// Object obj = getFieldValue(bean, interfaceClass);
	// return (T) Mockito.spy(obj);
	// }

	// public static Object proxy(Object bean, String fieldName) {
	// Object obj = BeanUtil.getFieldValue(bean, fieldName);
	// return Mockito.spy(obj);
	// }

	// public static <T> void proxy(Object bean, Callback<T> callback) {
	// Field[] fields = bean.getClass().getDeclaredFields();
	// for (Field field : fields) {
	// field.setAccessible(true);
	// try {
	// @SuppressWarnings("unchecked")
	// T obj = (T) field.get(bean);
	// obj = Mockito.spy(obj);
	// callback.execute(obj);
	// }
	// catch (ClassCastException e) {
	// continue;
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// }
	//
	// }

	@SuppressWarnings("unchecked")
	public static <T> void reset(T object) {
		Mockito.reset(object);
	}

	// if (clazz.getSuperclass() == null) {
	// String className = bean.getClass().getSimpleName();
	// throw new RuntimeException("clazz:" + className + " " + e.getMessage(),
	// e);
	// }
	// clazz = clazz.getSuperclass();

	// protected static List<Field> getFieldList(Object bean, Class<?>
	// interfaceClass) {
	// String className = interfaceClass.getName();
	// Class<?> clazz = bean.getClass();
	// List<Field> fieldList = new ArrayList<Field>();
	// while (true) {
	// Field[] fields = clazz.getDeclaredFields();
	// for (Field field : fields) {
	// if (className.equals(field.getType().getName())) {
	// fieldList.add(field);
	// }
	// }
	// if (clazz.getSuperclass() == null) {
	// break;
	// }
	// clazz = clazz.getSuperclass();
	// }
	// return fieldList;
	// }

	// private Date getTestDate() {
	// return null;
	// }
	//
	// public void testFindBugs() {
	// // int code = -5;
	// // boolean flag = code % 2 == 1;
	// // boolean flag2 = code % 2 == 1;
	// // System.out.println("flag:" + flag);
	// // System.out.println("flag:" + flag2);
	// Date date = getTestDate();
	// System.out.println("time:" + date.getTime());
	// System.out.println("Hell FindBugs");
	// }
}
