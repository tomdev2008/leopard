package io.leopard.test.mock.proxy;

import io.leopard.test.mock.reflect.MethodUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ MethodUtil.class })
public class MethodHandlerAssertWhenImplTest {

	public static class UserService {

		public boolean exist(String username) {
			return true;
		}

		public String getNickname(String username) {
			return "ahai";
		}
	}

	public static class UserDao {

	}

	@Test
	public void MethodHandlerAssertWhenImpl() {
		UserService userService = new UserService();
		UserDao userDao = new UserDao();
		PowerMockito.when(MethodUtil.getDaoValue(userService)).thenReturn(userDao);
		new MethodHandlerAssertWhenImpl(userService, null);
		new MethodHandlerAssertWhenImpl(userService, userDao);

		{
			PowerMockito.when(MethodUtil.getDaoValue(userService)).thenReturn(null);
			try {
				new MethodHandlerAssertWhenImpl(userService, null);
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void invoke() throws Throwable {
		UserService userService = new UserService();
		UserDao userDao = new UserDao();
		PowerMockito.when(MethodUtil.getDaoValue(userService)).thenReturn(userDao);
		MethodHandlerAssertWhenImpl handler = Mockito.spy(new MethodHandlerAssertWhenImpl(userService, null));

		Method method = UserService.class.getDeclaredMethod("exist", String.class);
		Object[] args = new Object[] { "hctan" };
		Mockito.doReturn(true).when(handler).invoke(Mockito.any(Method.class), Mockito.any(Object[].class), Mockito.any(Object.class));

		Assert.assertTrue((Boolean) handler.invoke(userService, method, method, args));
		handler.toBeReturned = "ok";
		Assert.assertTrue((Boolean) handler.invoke(userService, method, method, args));

	}

	@Test
	public void invoke2() throws Throwable {
		UserService userService = new UserService();
		UserDao userDao = new UserDao();
		PowerMockito.when(MethodUtil.getDaoValue(userService)).thenReturn(userDao);
		MethodHandlerAssertWhenImpl handler = Mockito.spy(new MethodHandlerAssertWhenImpl(userService, null));

		Method method = UserService.class.getDeclaredMethod("getNickname", String.class);
		Object[] args = new Object[] { "hctan" };
		Mockito.doReturn(true).when(handler).invoke(Mockito.any(Method.class), Mockito.any(Object[].class), Mockito.any(Object.class));

		Assert.assertTrue((Boolean) handler.invoke(userService, method, method, args));

	}

	@Test
	public void invoke3() throws Throwable {
		UserService userService = new UserService();
		UserDao userDao = new UserDao();
		PowerMockito.when(MethodUtil.getDaoValue(userService)).thenReturn(userDao);
		MethodHandlerAssertWhenImpl handler = Mockito.spy(new MethodHandlerAssertWhenImpl(userService, null));

		Method method = UserService.class.getDeclaredMethod("getNickname", String.class);
		Object[] args = new Object[] { "hctan" };
		// Mockito.doReturn(true).when(handler).invoke(Mockito.any(Method.class),
		// Mockito.any(Object[].class), Mockito.any(Object.class));
		PowerMockito.when(MethodUtil.getDefaultValue(String.class)).thenReturn("ahai");
		Assert.assertEquals("ahai", (String) handler.invoke(method, args, null));

	}

	@Test
	public void invoke4() throws Throwable {
		UserService userService = new UserService();
		UserDao userDao = new UserDao();
		PowerMockito.when(MethodUtil.getDaoValue(userService)).thenReturn(userDao);
		MethodHandlerAssertWhenImpl handler = Mockito.spy(new MethodHandlerAssertWhenImpl(userService, userDao, "ahai"));

		Method method = UserService.class.getDeclaredMethod("getNickname", String.class);
		Object[] args = new Object[] { "hctan" };
		// Mockito.doReturn(true).when(handler).invoke(Mockito.any(Method.class),
		// Mockito.any(Object[].class), Mockito.any(Object.class));
		// PowerMockito.when(MethodUtil.getDefaultValue(String.class)).thenReturn("ahai");
		Assert.assertEquals("ahai", (String) handler.invoke(method, args, "ahai"));

	}

	public static class ArticleService {

		ArticleDao articleDao = new ArticleDao();

		public String getNickname(String username) {
			return articleDao.getNickname(username);
		}
	}

	public static class ArticleDao {
		public String getNickname(String username) {
			return "ahai";
		}
	}

	@Test
	public void invoke5() throws Throwable {

	}

	@Test
	public void when() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ArticleService articleService = new ArticleService();
		ArticleDao articleDao = new ArticleDao();
		PowerMockito.when(MethodUtil.getDaoValue(articleService)).thenReturn(articleDao);

		Method method = ArticleService.class.getDeclaredMethod("getNickname", String.class);
		Method daoMethod = ArticleDao.class.getDeclaredMethod("getNickname", String.class);
		// Object[] daoArgs = new Object[] { "ahai" };
		Object[] args = new Object[] { "ahai" };

		// Object[] daoMethodInfos = new Object[] { daoMethod, daoArgs };
		Object daoValue = "ahai";

		// PowerMockito.when(DaoMethodUtil.getDaoMethod(Mockito.any(ArticleDao.class),
		// Mockito.any(Method.class),
		// Mockito.any(Object[].class))).thenReturn(daoMethodInfos);
		PowerMockito.when(MethodUtil.getGreaterEqualMethod(articleDao, method, args)).thenReturn(daoMethod);

		MethodHandlerAssertWhenImpl handler = Mockito.spy(new MethodHandlerAssertWhenImpl(articleService, articleDao, "ahai"));

		// Method daoMethod = MethodUtil.getGreaterEqualMethod(articleDao,
		// method, args);
		//
		// Assert.assertNotNull(daoMethod);

		handler.when(method, args, daoValue);
	}
}