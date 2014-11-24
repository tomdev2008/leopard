package io.leopard.test.mock.internal;

import org.junit.Assert;
import org.junit.Test;

public class AssertModelTest {
	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	public static class User2 {
		protected String username;

		public String getUsername() {
			throw new RuntimeException("msg");
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void assertModel() {
		AssertModel.assertModel(User.class);
		try {
			AssertModel.assertModel(User2.class);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void AssertModel() {
		new AssertModel();
	}

	@Test
	public void isValidMethodName() {
		Assert.assertTrue(AssertModel.isValidMethodName("getNickname"));
		Assert.assertTrue(AssertModel.isValidMethodName("setNickname"));
		Assert.assertTrue(AssertModel.isValidMethodName("toString"));
		Assert.assertTrue(AssertModel.isValidMethodName("isMember"));
		Assert.assertFalse(AssertModel.isValidMethodName("nickname"));
	}

}