package io.leopard.test.mock.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AssertListTest {
	public static class User {
		private String username;

		public User() {

		}

		public User(String username) {
			setUsername(username);
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void list() {
		{
			List<String> list = new ArrayList<String>();
			list.add("a");
			list.add("b");
			AssertList.list("a,b", list);
		}
		{
			List<User> userList = new ArrayList<User>();
			userList.add(new User("a"));
			userList.add(new User("b"));
			AssertList.list("[username:a;username:b]", userList);
		}
		AssertList.list(null, null);
	}

	@Test
	public void AssertList() {
		new AssertList();
	}

	@Test
	public void toStringValue() {
		Assert.assertNull(AssertList.toStringValue(null));
		Assert.assertEquals("str", AssertList.toStringValue("str"));
		Assert.assertEquals("1", AssertList.toStringValue(new Date(1)));
	}

}