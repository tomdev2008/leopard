package io.leopard.commons.utility;

import io.leopard.core.exception.NoSuchFieldRuntimeException;

import org.junit.Assert;
import org.junit.Test;

public class BeanUtilTest {

	@Test
	public void instantiateClass() {
		User user = BeanUtil.instantiateClass(User.class, "hctan");
		Assert.assertNotNull(user);
		Assert.assertEquals("hctan", user.getUsername());
	}

	@Test
	public void isNull() {
		Assert.assertTrue(BeanUtil.isNull(null));
		Assert.assertFalse(BeanUtil.isNull(new String()));
	}

	@Test
	public void isNotNull() {
		Assert.assertFalse(BeanUtil.isNotNull(null));
		Assert.assertTrue(BeanUtil.isNotNull(new String()));
	}

	@Test
	public void getPropertyValue() {
		Assert.assertNull(BeanUtil.getPropertyValue(null, "username"));
		User user = new User();
		user.setUsername("hctan");
		Assert.assertEquals("hctan", BeanUtil.getPropertyValue(user, "username"));
	}

	@Test
	public void getFieldValue() {
		User user = new User();
		user.setUsername("hctan");
		Assert.assertEquals("hctan", BeanUtil.getFieldValue(user, "username"));

		{
			try {
				BeanUtil.getFieldValue(user, "usernamexx");
				Assert.fail("怎么没有抛异常?");
			}
			catch (NoSuchFieldRuntimeException e) {

			}
		}
	}

	@Test
	public void getField() {
		User user = new User();
		user.setUsername("hctan");
		Assert.assertNotNull(BeanUtil.getField(user, "username"));
		{
			try {
				BeanUtil.getField(user, "usernamexx");
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	protected static class User {
		private String username;

		public User() {

		}

		public User(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void setFieldValue() {
		User user = new User();
		BeanUtil.setFieldValue(user, "username", "hctan");
		Assert.assertEquals("hctan", user.getUsername());

		{
			try {
				BeanUtil.setFieldValue(user, "usernamexx", "hctan");
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

}