package io.leopard.test.mock.template;

import io.leopard.data4j.cache.api.IAdd;
import io.leopard.data4j.cache.api.IDecr;
import io.leopard.data4j.cache.api.IDelete;
import io.leopard.data4j.cache.api.IGet;
import io.leopard.data4j.cache.api.IGetIncludeDeleted;
import io.leopard.data4j.cache.api.IIncr;
import io.leopard.data4j.cache.api.IList;
import io.leopard.data4j.cache.api.IUnDelete;
import io.leopard.reflect.CtClassUtil;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test.mock.reflect.Tson;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.util.proxy.MethodHandler;

import org.junit.Assert;

public class MethodHandlerAddImpl<T> implements MethodHandler {

	private final T mock;
	private final String textJson;

	public MethodHandlerAddImpl(final T mock, final String textJson) {
		this.mock = mock;
		this.textJson = textJson;
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		String methodName = thisMethod.getName();
		return this.invoke(thisMethod, args, methodName);
	}

	protected Object invoke(Method thisMethod, Object[] args, String methodName) throws Throwable {
		if (methodName.equals("delete")) {
			return this.delete(thisMethod, args);
		}
		else if (methodName.equals("incr")) {
			return this.incr(thisMethod, args);
		}
		else if (methodName.equals("decr")) {
			return this.decr(thisMethod, args);
		}
		else if (methodName.equals("remove")) {
			return this.remove(thisMethod, args);
		}
		else if (methodName.equals("undelete")) {
			return this.undelete(thisMethod, args);
		}
		else if (methodName.equals("exist")) {
			return this.exist(thisMethod, args);
		}
		else if (methodName.equals("add")) {
			throw new RuntimeException("请使用get方法测试add接口.");
		}
		else if (methodName.equals("get")) {
			return this.get(thisMethod, args);
		}
		else if (methodName.equals("getIncludeDeleted")) {
			return this.getIncludeDeleted(thisMethod, args);
		}
		else if (methodName.startsWith("get")) {
			return this.getXxx(thisMethod, args);
		}
		else if (methodName.equals("map")) {
			return this.map(thisMethod, args);
		}
		else if (methodName.equals("list")) {
			Class<?> param = thisMethod.getParameterTypes()[0];
			if (param.equals(List.class)) {
				return this.list(thisMethod, args);
			}
			else {
				return this.listXxx(thisMethod, args);
			}
		}
		else if (methodName.startsWith("delete")) {
			return this.deleteXxx(thisMethod, args);
		}
		else if (methodName.startsWith("update")) {
			return this.update(thisMethod, args);
		}
		else if (methodName.startsWith("count")) {
			return this.count(thisMethod, args);
		}
		else if (methodName.startsWith("list")) {
			return this.listXxx(thisMethod, args);
		}
		throw new RuntimeException("未知方法[" + methodName + "].");
	}

	protected Object count(Method method, Object[] args) throws Exception {
		String[] names = CtClassUtil.getParameterNames(method);
		String textJson2 = Tson.toTextJson(textJson, names, args);
		Assert.assertTrue(Invoke.addXxx(mock, textJson2));
		int count = (Integer) method.invoke(mock, args);
		Assert.assertEquals("count怎么不是1？", count, count);
		return 0;
	}

	protected Object listXxx(Method method, Object[] args) throws Exception {
		String[] names = CtClassUtil.getParameterNames(method);
		Set<String> ignoreNameSet = new HashSet<String>();
		ignoreNameSet.add("start");// 列表记录开始位置
		ignoreNameSet.add("size");// 列表记录条数
		ignoreNameSet.add("startTime");// 开始时间
		ignoreNameSet.add("endTime");// 结束时间
		String textJson2 = Tson.toTextJson(textJson, names, args, ignoreNameSet);
		// System.out.println("textJson2:" + textJson2);
		Assert.assertTrue("add方法怎么没有返回true?", Invoke.addXxx(mock, textJson2));

		// List<?> list = (List<?>) MethodUtil.invoke(mock, method, args);
		List<?> list = (List<?>) method.invoke(mock, args);
		// Json.print(list, "list");
		Assert.assertNotNull("list怎么会为空?", list);
		Assert.assertEquals("list的size怎么不是1？", 1, list.size());
		return null;
	}

	protected Object list(Method method, Object[] args) {
		@SuppressWarnings("unchecked")
		List<Object> keyList = (List<Object>) args[0];
		// System.out.println("keyList:" + keyList);

		Method getMethod;
		try {
			// 为了兼容Converage插件
			Class<?> parameterType = keyList.get(0).getClass();
			getMethod = method.getDeclaringClass().getMethod("get", parameterType);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		@SuppressWarnings("unchecked")
		IList<Object, Object> dao = (IList<Object, Object>) mock;

		for (Object key : keyList) {
			Object bean = Reflect.makeBean(dao, getMethod, new Object[] { key }, textJson);
			Assert.assertTrue("add方法怎么没有返回true?", dao.add(bean));
		}

		List<Object> list = dao.list(keyList);
		// Json.print(list, "list");
		Assert.assertNotNull("list怎么会为空?", list);
		Assert.assertEquals("list的size怎么不是" + keyList.size() + "？", keyList.size(), list.size());
		return null;
	}

	protected Object map(Method method, Object[] args) {

		@SuppressWarnings("unchecked")
		Set<Object> keySet = (Set<Object>) args[0];

		Method getMethod;
		try {
			Class<?> parameterType = keySet.iterator().next().getClass();
			getMethod = method.getDeclaringClass().getMethod("get", parameterType);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// System.out.println("getMethod:" + getMethod.toGenericString());
		String[] names = CtClassUtil.getParameterNames(getMethod);
		Set<String> ignoreNameSet = new HashSet<String>();
		ignoreNameSet.add("start");
		ignoreNameSet.add("size");
		ignoreNameSet.add("startTime");

		for (Object key : keySet) {
			String textJson2 = Tson.toTextJson(textJson, names, new Object[] { key }, ignoreNameSet);
			Assert.assertTrue("add方法怎么没有返回true?", Invoke.addXxx(mock, textJson2));
		}

		Map<?, ?> map = (Map<?, ?>) MethodUtil.invoke(mock, method, args);
		// Json.print(list, "list");
		Assert.assertNotNull("map怎么会为空?", map);
		Assert.assertEquals("map的size怎么不是" + keySet.size() + "？", keySet.size(), map.size());
		return map;
	}

	protected Object update(Method method, Object[] args) {
		String[] names = CtClassUtil.getParameterNames(method);
		String textJson2 = Tson.toTextJson(textJson, names, args);
		Assert.assertTrue(Invoke.addXxx(mock, textJson2));
		boolean success = (Boolean) MethodUtil.invoke(mock, method, args);
		Assert.assertTrue("更新方法怎么不返回true?", success);
		return success;
	}

	protected Long incr(Method method, Object[] args) {
		if (!(mock instanceof IIncr)) {
			throw new RuntimeException("对象未实现IIncr.");
		}

		Object key = args[0];
		int count = 5;// (Integer) args[1];
		if (mock instanceof IGet) {
			@SuppressWarnings("unchecked")
			IGet<Object, Object> dao = (IGet<Object, Object>) mock;
			Object[] args2 = new Object[] { key, 0 };// 添加count字段默认为0
			Object bean = Reflect.makeBean(dao, method, args2, textJson);
			Assert.assertTrue("怎么添加记录不返回true?", dao.add(bean));
		}
		@SuppressWarnings("unchecked")
		IIncr<Object> dao = (IIncr<Object>) mock;

		if (true) {
			Long num = dao.incr(key, count);
			if (num != null && num > 1) {
				int total = count;
				Assert.assertEquals("incr返回值怎么不是" + total + "?", num.longValue(), total);
			}
		}

		Long num = dao.incr(key, count);
		if (num != null && num > 1) {
			int total = count * 2;
			Assert.assertEquals("incr返回值怎么不是" + total + "?", num.longValue(), total);
		}
		return num;
	}

	protected Long decr(Method method, Object[] args) {
		if (!(mock instanceof IDecr)) {
			throw new RuntimeException("未知对象.");
		}

		Object key = args[0];
		int count = 5;// (Integer) args[1];
		if (mock instanceof IGet) {
			@SuppressWarnings("unchecked")
			IGet<Object, Object> dao = (IGet<Object, Object>) mock;
			Object[] args2 = new Object[] { key, 0 };// 添加count字段默认为0
			Object bean = Reflect.makeBean(dao, method, args2, textJson);
			Assert.assertTrue("怎么添加记录不返回true?", dao.add(bean));
		}

		int defCount = 10000;
		@SuppressWarnings("unchecked")
		IDecr<Object> dao = (IDecr<Object>) mock;
		dao.incr(key, defCount);

		if (true) {
			Long num = dao.decr(key, count);
			int total = defCount - count;
			Assert.assertEquals("decr返回值怎么不是" + total + "?", num.longValue(), total);
		}

		Long num = dao.decr(key, count);
		int total = defCount - count * 2;
		Assert.assertEquals("decr返回值怎么不是" + total + "?", num.longValue(), total);
		return num;

	}

	protected Object get(Method method, Object[] args) {
		if (!(mock instanceof IGet)) {
			String className = mock.getClass().getSuperclass().getName();
			throw new RuntimeException("对象[" + className + "]没有实现IGet接口.");
		}

		@SuppressWarnings("unchecked")
		IGet<Object, Object> dao = (IGet<Object, Object>) mock;
		Object bean = Reflect.makeBean(dao, method, args, textJson);
		Assert.assertTrue("怎么添加记录不返回true?", dao.add(bean));
		Object result = dao.get(args[0]);
		Assert.assertNotNull("怎么刚刚添加的记录[" + args[0] + "]查不出来?", result);
		return result;
	}

	protected Object getXxx(Method method, Object[] args) throws Exception {
		@SuppressWarnings("unchecked")
		IGet<Object, Object> dao = (IGet<Object, Object>) mock;
		Class<?> modelType = method.getReturnType();
		Object bean = Reflect.makeBean(dao, method, args, textJson, modelType);
		// Json.print(bean, "bean");
		Assert.assertTrue("怎么添加记录不返回true?", dao.add(bean));
		Object result = method.invoke(dao, args);
		Assert.assertNotNull("怎么刚刚添加的记录查不出来?", result);
		return result;
	}

	protected Object getIncludeDeleted(Method method, Object[] args) {
		// String[] names =
		// ClassUtil.getParameterNames(method.getDeclaringClass(), method);
		// String textJson2 = TextJson.toTextJson(textJson, names, args);

		Object key = args[0];

		@SuppressWarnings("unchecked")
		IGetIncludeDeleted<Object, Object> dao = (IGetIncludeDeleted<Object, Object>) mock;

		Object bean = Reflect.makeBean(dao, method, args, textJson);

		Assert.assertTrue("怎么添加记录不返回true?", dao.add(bean));
		Assert.assertNotNull("怎么刚刚添加的记录查不出来?", dao.get(key));
		Assert.assertTrue("怎么删除记录不返回true?", dao.delete(key, "username", new Date()));
		Assert.assertNull("怎么刚刚删除的记录还能get出来?", dao.get(key));

		Assert.assertNotNull("怎么刚刚标记删除的记录，使用getDeleted方法查不出来?", dao.getIncludeDeleted(key));

		return null;
	}

	protected boolean exist(Method method, Object[] args) throws Exception {
		@SuppressWarnings("unchecked")
		IAdd<Object> dao = (IAdd<Object>) mock;
		Object bean = Reflect.makeBean(dao, method, args, textJson);
		Assert.assertTrue("add方法怎么不返回true?", dao.add(bean));

		boolean result = (Boolean) method.invoke(mock, args);
		Assert.assertTrue("exist方法没有返回true?", result);
		return result;
	}

	protected boolean remove(Method method, Object[] args) throws Exception {
		if (!(mock instanceof IGet)) {
			throw new RuntimeException("测试Remove方法，DAO必须实现IGet标准接口.");
		}

		Object key = args[0];
		@SuppressWarnings("unchecked")
		IGet<Object, Object> dao = (IGet<Object, Object>) mock;
		Object bean = Reflect.makeBean(dao, method, args, textJson);

		method.setAccessible(true);

		Assert.assertTrue("add方法怎么不返回true?", dao.add(bean));
		Assert.assertNotNull("怎么刚添加的数据get不出来?", dao.get(key));

		Assert.assertTrue("remove方法没有返回true?", (Boolean) method.invoke(mock, args));
		Assert.assertFalse("重复remove，没有返回false", (Boolean) method.invoke(mock, args));
		return true;
	}

	protected boolean deleteXxx(Method method, Object[] args) throws Exception {
		@SuppressWarnings("unchecked")
		IAdd<Object> dao = (IAdd<Object>) mock;
		Object bean = Reflect.makeBean(dao, method, args, textJson);
		Assert.assertTrue("add方法怎么不返回true?", dao.add(bean));

		Assert.assertTrue("怎么删除返回值不是true?", (Boolean) method.invoke(mock, args));
		Assert.assertFalse("怎么重复删除返回值不是false?", (Boolean) method.invoke(mock, args));

		return true;
	}

	protected boolean delete(Method method, Object[] args) {
		if (mock instanceof io.leopard.data4j.cache.api.uid.IDelete) {
			return this.deleteYyuid(method, args);
		}
		if (!(mock instanceof IDelete)) {
			String className = mock.getClass().getSuperclass().getName();
			throw new RuntimeException("对象[" + className + "]没有实现IDelete接口.");
		}
		@SuppressWarnings("unchecked")
		IDelete<Object, Object> dao = (IDelete<Object, Object>) mock;
		Object key = args[0];
		String username = (String) args[1];
		Date lmodify = (Date) args[2];

		Object bean = Reflect.makeBean(dao, method, args, textJson);
		Assert.assertTrue("add方法怎么不返回true?", dao.add(bean));
		Assert.assertNotNull("怎么刚刚添加的记录查不出来？", dao.get(key));
		Assert.assertTrue("怎么删除返回值不是true?", dao.delete(key, username, lmodify));
		Assert.assertFalse("怎么重复删除返回值不是false?", dao.delete(key, username, lmodify));
		Assert.assertNull("怎么get方法还能返回数据?", dao.get(key));
		return true;
	}

	protected boolean deleteYyuid(Method method, Object[] args) {
		@SuppressWarnings("unchecked")
		io.leopard.data4j.cache.api.uid.IDelete<Object, Object> dao = (io.leopard.data4j.cache.api.uid.IDelete<Object, Object>) mock;

		Object key = args[0];
		long yyuid = (Long) args[1];
		Date lmodify = (Date) args[2];

		Object bean = Reflect.makeBean(dao, method, args, textJson);
		Assert.assertTrue("add方法怎么不返回true?", dao.add(bean));
		Assert.assertNotNull("怎么刚刚添加的记录查不出来？", dao.get(key));
		Assert.assertTrue("怎么删除返回值不是true?", dao.delete(key, yyuid, lmodify));
		Assert.assertFalse("怎么重复删除返回值不是false?", dao.delete(key, yyuid, lmodify));
		Assert.assertNull("怎么get方法还能返回数据?", dao.get(key));
		return true;
	}

	protected boolean undelete(Method method, Object[] args) {

		Object key = args[0];
		String username = (String) args[1];
		Date lmodify = (Date) args[2];

		@SuppressWarnings("unchecked")
		IUnDelete<Object, Object> dao = (IUnDelete<Object, Object>) mock;

		Object bean;
		{
			String[] names = CtClassUtil.getParameterNames(method);
			String textJson2 = Tson.toTextJson(textJson, names, args);
			Class<?> modelType = Reflect.getReturnType(dao, key);
			bean = Mock.newInstance(textJson2, modelType);
		}

		Assert.assertTrue("add方法怎么不返回true?", dao.add(bean));
		Assert.assertNotNull("怎么刚刚添加的记录查不出来?", dao.get(key));
		Assert.assertTrue("怎么删除返回值不是true?", dao.delete(key, username, lmodify));
		// Assert.assertTrue("怎么撤销删除返回值不是true?", dao.undelete(key, username,
		// lmodify));
		boolean success = dao.undelete(key, username, lmodify);
		Assert.assertFalse("怎么重复撤销删除返回值不是false?", dao.undelete(key, username, lmodify));
		if (success) {
			Assert.assertNotNull("怎么撤销删除的数据查不出来?", dao.get(key));
		}
		return true;
	}

}
