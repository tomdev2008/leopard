package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ModelInstanceUtilTest {

	@Test
	public void ModelInstanceUtil() {
		new ModelInstanceUtil();
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

	@Test
	public void getNewInstance() {
		User user = ModelInstanceUtil.getNewInstance(User.class);
		Assert.assertNotNull(user);
	}

	@Test
	public void getDefaultValue2() {
		Object[] args = ModelInstanceUtil.getDefaultValue(new Class[] { String.class });
		Assert.assertEquals("str", args[0]);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void getDefaultValue() {
		Assert.assertEquals("str", ModelInstanceUtil.getDefaultValue(String.class));
		Assert.assertEquals(0, ModelInstanceUtil.getDefaultValue(Integer.class));
		Assert.assertEquals(0L, ModelInstanceUtil.getDefaultValue(Long.class));
		Assert.assertEquals(0F, ModelInstanceUtil.getDefaultValue(Float.class));
		Assert.assertEquals(0D, ModelInstanceUtil.getDefaultValue(Double.class));
		Assert.assertEquals(0, ModelInstanceUtil.getDefaultValue(BigDecimal.class));
		Assert.assertEquals(false, ModelInstanceUtil.getDefaultValue(Boolean.class));
		Assert.assertEquals('a', ModelInstanceUtil.getDefaultValue(Character.class));
		Assert.assertEquals(1L, ((Date) ModelInstanceUtil.getDefaultValue(Date.class)).getTime());
		Assert.assertEquals(1L, ((Timestamp) ModelInstanceUtil.getDefaultValue(Timestamp.class)).getTime());
		Assert.assertEquals("2000-01-01", ((OnlyDate) ModelInstanceUtil.getDefaultValue(OnlyDate.class)).toString());
		Assert.assertEquals("2000-01", ((Month) ModelInstanceUtil.getDefaultValue(Month.class)).toString());
		Assert.assertEquals(0, ((List) ModelInstanceUtil.getDefaultValue(List.class)).size());
	}

	@Test
	public void to() {
		Assert.assertEquals("str", ModelInstanceUtil.to("str", String.class));
		Assert.assertEquals(2, (int) (Integer) ModelInstanceUtil.to("2", Integer.class));
		Assert.assertEquals(2L, (long) (Long) ModelInstanceUtil.to("2", Long.class));
		try {
			ModelInstanceUtil.to("2", Float.class);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}
}