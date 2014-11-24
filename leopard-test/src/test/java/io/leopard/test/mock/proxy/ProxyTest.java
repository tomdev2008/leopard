package io.leopard.test.mock.proxy;

import io.leopard.test.mock.proxy.Proxy.ProxyMethodHandlerImpl;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ProxyTest {

	@Test
	public void Proxy() {
		new Proxy();
	}

	public static class UserService {

		@Override
		protected void finalize() throws Throwable {

		}

		protected String getNickname(String username) {
			return "ahai";
		}
	}

	@Test
	public void getMethodFilter() throws SecurityException, NoSuchMethodException {
		ProxyMethodHandlerImpl handler = new ProxyMethodHandlerImpl();
		MethodFilter filter = handler.getMethodFilter();
		{
			Method method = UserService.class.getDeclaredMethod("finalize");
			Assert.assertFalse(filter.isHandled(method));
		}
		{
			Method method = UserService.class.getDeclaredMethod("getNickname", String.class);
			Assert.assertTrue(filter.isHandled(method));
		}
	}

	@Test
	public void getMethodHandler() throws Throwable {
		ProxyMethodHandlerImpl handler = new ProxyMethodHandlerImpl();
		UserService proxy = new UserService();
		Method method = UserService.class.getDeclaredMethod("getNickname", String.class);
		Object[] args = new Object[] { "hctan" };
		handler.getMethodHandler().invoke(proxy, method, method, args);
	}

	@Test
	public void getMethodHandler2() throws Throwable {
		MethodHandler methodHandler = new MethodHandler() {
			public Object invoke(final Object proxy, Method method, final Method proceed, final Object[] args) throws Throwable {
				System.out.println("method:" + method.toGenericString());
				System.out.println("proceed:" + proceed.toGenericString());
				return proceed.invoke(proxy, args);
			}
		};

		ProxyMethodHandlerImpl handler = new ProxyMethodHandlerImpl(methodHandler);
		UserService proxy = new UserService();
		Method method = UserService.class.getDeclaredMethod("getNickname", String.class);
		Object[] args = new Object[] { "hctan" };
		handler.getMethodHandler().invoke(proxy, method, method, args);
	}

	@Test
	public void newProxyInstance2() {
		{
			UserService userService = new UserService();
			Proxy.newProxyInstance(userService, (MethodHandler) null);
		}
		{
			UserService userService = Mockito.spy(new UserService());
			Proxy.newProxyInstance(userService, (MethodHandler) null);
		}
	}

	@Test
	public void newProxyInstance() {
		Proxy.newProxyInstance(UserService.class, (MethodHandler) null);
		Proxy.newProxyInstance(UserService.class, new ProxyMethodHandlerImpl());
		try {
			Proxy.newProxyInstance(UserService.class, new ProxyMethodHandlerImpl() {
				@Override
				public MethodHandler getMethodHandler() {
					throw new RuntimeException();
				}
			});
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}
}