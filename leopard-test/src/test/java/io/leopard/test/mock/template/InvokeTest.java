package io.leopard.test.mock.template;

import io.leopard.data4j.cache.api.IDelete;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InvokeTest {

	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static class UserDaoMysqlImpl implements IDelete<User, String> {

		public User get(String username) {
			User user = new User();
			user.setUsername(username);
			return user;
		}

		@Override
		public boolean add(User user) {
			return false;
		}

		@Override
		public boolean delete(String key, String opusername, Date lmodify) {
			return false;
		}
	}

	@Test
	public void hasGet() {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		Assert.assertTrue(Invoke.hasGet(userDao));
	}

	@Test
	public void get() {
		UserDaoMysqlImpl userDao = Mockito.spy(new UserDaoMysqlImpl());
		Invoke.get(userDao, "key");
		Mockito.verify(userDao).get("key");
	}

	@Test
	public void delete() {
		Date lmodify = new Date();
		UserDaoMysqlImpl userDao = Mockito.spy(new UserDaoMysqlImpl());
		Invoke.delete(userDao, "key", "opusername", lmodify);
		Mockito.verify(userDao).delete("key", "opusername", lmodify);
	}

	@Test
	public void getDefaultValue() {
		Assert.assertEquals(1, Invoke.getDefaultValue(Integer.class, "1"));
		Assert.assertEquals(1L, Invoke.getDefaultValue(Long.class, "1"));
		Assert.assertEquals("1", Invoke.getDefaultValue(String.class, "1"));
		try {
			Invoke.getDefaultValue(Date.class, "1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void Invoke() {
		new Invoke();
	}

	@Test
	public void update() {
		class UserDao {
			@SuppressWarnings("unused")
			public boolean update() {
				return true;
			}
		}
		UserDao userDao = new UserDao();
		try {
			Invoke.update(userDao, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void update2() {
		class UserDao {
			@SuppressWarnings("unused")
			public boolean update(String username) {
				return true;
			}
		}
		UserDao userDao = new UserDao();
		try {
			Invoke.update(userDao, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	// @Test
	// public void add() {
	// UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
	// Invoke.add(userDao, "{username:hctan}");
	// Mockito.verify(userDao).add(Mockito.any(User.class));
	// }

	@Test
	public void addXxx() {
		class UserDao {
			@SuppressWarnings("unused")
			public boolean update(String username) {
				return true;
			}
		}
		UserDao userDao = new UserDao();
		try {
			Invoke.addXxx(userDao, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void addXxx2() {
		class UserDao {
			@SuppressWarnings("unused")
			public boolean add() {
				return true;
			}
		}
		UserDao userDao = new UserDao();
		try {
			Invoke.addXxx(userDao, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	public static class UserDao3 {
		public boolean add(User user) {
			return true;
		}
	}

	@Test
	public void addXxx3() {

		UserDao3 userDao = new UserDao3();

		Invoke.addXxx(userDao, "");

	}
}