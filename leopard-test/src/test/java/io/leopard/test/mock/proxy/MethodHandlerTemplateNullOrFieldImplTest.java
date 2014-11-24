package io.leopard.test.mock.proxy;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MethodHandlerTemplateNullOrFieldImplTest {

	@Test
	public void MethodHandlerTemplateNullOrFieldImpl() {
		new MethodHandlerTemplateNullOrFieldImpl(null, null, null, null);
	}

	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static class UserDao {
		public User get(String username) {
			return null;
		}

		public String getNickname(String username) {
			return null;
		}

	}

	public static class UserService {
		private UserDao userDao = new UserDao();

		public User get(String username) {
			return userDao.get(username);
		}

		public String getNickname(String username) {
			return userDao.getNickname(username);
		}

	}

	@Test
	public void getRealReturnType() {
		{
			MethodHandlerTemplateNullOrFieldImpl handler = new MethodHandlerTemplateNullOrFieldImpl(null, null, null, "{username:hctan}");
			handler.getRealReturnType(User.class);
		}
		{
			MethodHandlerTemplateNullOrFieldImpl handler = new MethodHandlerTemplateNullOrFieldImpl(null, null, null, "{nickname:hctan}");
			try {
				handler.getRealReturnType(User.class);
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void mock() {
		UserDao userDao = Mockito.spy(new UserDao());
		MethodHandlerTemplateNullOrFieldImpl handler = new MethodHandlerTemplateNullOrFieldImpl(null, userDao, null, "{username:hctan}");
		handler.mock("get", "{username:hctan}", new Object[] { "hctan" });

		handler.mock("get", null, new Object[] { "hctan" });
		handler.mock("getNickname", null, new Object[] { "hctan" });

	}

}