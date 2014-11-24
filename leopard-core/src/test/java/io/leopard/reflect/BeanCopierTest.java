package io.leopard.reflect;

import org.junit.Assert;
import org.junit.Test;

public class BeanCopierTest {
	protected static class User {
		private String username;
		private String nickname;

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

	}

	public static class UserVO extends User {
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public static UserVO toUserVO(User user) {
			return BeanCopier.copy(user, UserVO.class);
		}
	}

	@Test
	public void copy() {
		{
			User user = new User();
			user.setUsername("username");
			user.setNickname("nickname");
			UserVO userVO = UserVO.toUserVO(user);

			Assert.assertEquals("username", userVO.getUsername());
			Assert.assertEquals("nickname", userVO.getNickname());
			Assert.assertNull(userVO.getContent());

		}

		{
			User user = new User();
			user.setUsername("username");
			user.setNickname("nickname");
			UserVO userVO = UserVO.toUserVO(user);

			Assert.assertEquals("username", userVO.getUsername());
			Assert.assertEquals("nickname", userVO.getNickname());
			Assert.assertNull(userVO.getContent());
		}
	}

	@Test
	public void BeanCopier() {
		new BeanCopier();
	}

	@Test
	public void createBeanCopier() {
		BeanCopier.createBeanCopier(User.class, UserVO.class);
	}

}