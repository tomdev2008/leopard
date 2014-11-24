package io.leopard.test.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class AssertEqualsTest {

	@Test
	public void AssertEquals() {
		new AssertEquals();
	}

	public static class User {
		private String username;
		private String nickname;

		private Date posttime;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public Date getPosttime() {
			return posttime;
		}

		public void setPosttime(Date posttime) {
			this.posttime = posttime;
		}

	}

	@Test
	public void equals() {
		AssertEquals.equals("str", "str");
		AssertEquals.equals(null, null);

		{
			User user = new User();
			user.setNickname("ahai");
			user.setUsername("hctan");
			user.setPosttime(new Date(1));
			AssertEquals.equals("{username:hctan,nickname:ahai,posttime:1}", user);
		}

		{
			List<User> userList = new ArrayList<User>();
			User user = new User();
			user.setNickname("ahai");
			user.setUsername("hctan");
			user.setPosttime(new Date(1));
			userList.add(user);

			AssertEquals.equals("[username:hctan,nickname:ahai,posttime:1]", userList);
		}
	}

	@Test
	public void equalsByTson() {
		try {
			AssertEquals.equalsByTson("str", "str");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

}