package io.leopard.test.mock.template;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ReflectTest {
	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static class UserDaoMysqlImpl {
		public User get(String username) {
			User user = new User();
			user.setUsername(username);
			return user;
		}
	}

	@Test
	public void Reflect() {
		new Reflect();
	}

	@Test
	public void printDeclaredMethods() {
		Reflect.printDeclaredMethods(ReflectTest.class);
	}

	@Test
	public void isDefaultDeleteMethod() {

	}

	@Test
	public void getRealClass() {
		Assert.assertEquals(UserDaoMysqlImpl.class.getName(), Reflect.getRealClass(new UserDaoMysqlImpl()).getName());
		UserDaoMysqlImpl userDaoMock = Mockito.mock(UserDaoMysqlImpl.class);
		Assert.assertEquals(UserDaoMysqlImpl.class.getName(), Reflect.getRealClass(userDaoMock).getName());
	}

	@Test
	public void getReturnType() {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		Class<?> returnType = Reflect.getReturnType(userDao, "hctan");
		System.out.println("returnType:" + returnType);
		Assert.assertEquals(User.class.getName(), returnType.getName());
	}

	@Test
	public void makeBean() throws SecurityException, NoSuchMethodException {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		Method method = UserDaoMysqlImpl.class.getMethod("get", String.class);
		Object[] args = { "hctan" };
		User user = (User) Reflect.makeBean(userDao, method, args, "");
//		System.out.println("obj:" + obj.getClass().getName());
		Assert.assertEquals("hctan", user.getUsername());
	}

}