package io.leopard.test.mock.template;

import io.leopard.data4j.cache.api.IGet;
import io.leopard.test.mock.reflect.Tson;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

import org.junit.Assert;

public class MethodHandlerUpdateImpl<T> implements MethodHandler {

	private final T mock;
	private final String textJson;
	private final boolean checkGetMethod;

	public MethodHandlerUpdateImpl(final T mock, final String textJson, final boolean checkGetMethod) {
		this.mock = mock;
		this.textJson = textJson;
		this.checkGetMethod = checkGetMethod;
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		// if (thisMethod.getName().equals("delete")) {
		// return this.delete(thisMethod, args);
		// }
		// else if (thisMethod.getName().equals("remove")) {
		// return this.remove(thisMethod, args);
		// }
		if (thisMethod.getName().equals("get")) {
			return this.get(thisMethod, args);
		}

		throw new RuntimeException("未知方法[" + thisMethod.getName() + "].");

	}

	protected Object get(Method method, Object[] args) throws Exception {
		String[] names = CtClassUtil.getParameterNames(method);
		String textJson2 = Tson.toTextJson(textJson, names, args);

		@SuppressWarnings("unchecked")
		IGet<Object, Object> dao = (IGet<Object, Object>) mock;
		Object bean = Reflect.makeBean(dao, method, args, textJson);

		Assert.assertTrue("怎么添加记录不返回true?", dao.add(bean));
		Assert.assertNotNull("怎么刚刚添加的记录查不出来?", dao.get(args[0]));

		Assert.assertTrue("怎么更新方法不返回true?", Invoke.update(dao, textJson2));
		if (checkGetMethod) {
			Assert.assertNotNull("怎么刚刚更新过的记录查不出来?", dao.get(args[0]));
		}
		return null;
	}
}
