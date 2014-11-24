package io.leopard.reflect;

import io.leopard.commons.utility.Clock;
import io.leopard.reflect.BeanCopier;

import org.junit.Test;

public class BeanCopierIntegrationTest {

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
		User user = new User();
		user.setUsername("username");
		user.setNickname("nickname");

		int size = 1000 * 10000;
		Clock clock = Clock.start(size);
		for (int i = 0; i < size; i++) {
			UserVO.toUserVO(user);
		}
		clock.avg("copy");
	}

}