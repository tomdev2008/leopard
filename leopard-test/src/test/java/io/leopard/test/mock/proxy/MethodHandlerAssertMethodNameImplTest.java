package io.leopard.test.mock.proxy;

import io.leopard.test4j.mock.LeopardMockito;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MethodHandlerAssertMethodNameImplTest {

	public static class UserService {
		private UserDao userDao = new UserDao();
		private ArticleDao articleDao = new ArticleDao();

		public String getNickname(String username) {
			return userDao.getNickname(username);
		}

		public String getTitle(String username) {
			return articleDao.getTitle(username);
		}
	}

	public static class UserDao {
		public String getNickname(String username) {
			return "ahai";
		}
	}

	public static class ArticleDao {
		public String getTitle(String username) {
			return "title";
		}
	}

	@Test
	public void MethodHandlerAssertMethodNameImpl() {
		UserService userService = new UserService();
		{
			MethodHandlerAssertMethodNameImpl handler = new MethodHandlerAssertMethodNameImpl(userService, "get");
			Assert.assertEquals("get", handler.methodName);
		}
		{
			MethodHandlerAssertMethodNameImpl handler = new MethodHandlerAssertMethodNameImpl(userService, "articleDao.getTitle");
			Assert.assertEquals("ArticleDao", handler.dao.getClass().getSimpleName());
			Assert.assertEquals("getTitle", handler.methodName);
		}

		{
			try {
				new MethodHandlerAssertMethodNameImpl(userService, "");
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}

		}

	}

	@Test
	public void invoke() throws Throwable {
		UserService userService = new UserService();
		ArticleDao articleDao = Mockito.spy(new ArticleDao());
		LeopardMockito.setProperty(userService, articleDao);

		MethodHandlerAssertMethodNameImpl handler = new MethodHandlerAssertMethodNameImpl(userService, "articleDao.getTitle");
		Object[] args = new Object[] { "hctan" };
		Method method = UserService.class.getDeclaredMethod("getTitle", String.class);
		handler.invoke(this, method, method, args);
	}
}