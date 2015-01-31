package io.leopard.burrow.refect;

import io.leopard.core.exception.NoSuchFieldRuntimeException;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FieldUtilTest {

	@Test
	public void FieldUtil() {
		new FieldUtil();
	}

	public static class UserDaoMysqlImpl {
		protected String username;
		protected String nickname;
		protected Long yyuid;
	}

	public static class UserDaoEmptyImpl {

	}

	@Test
	public void listFields() {
		List<Field> fieldList = FieldUtil.listFields(new UserDaoMysqlImpl(), String.class);
		Assert.assertEquals(2, fieldList.size());
	}

	@Test
	public void setFieldValue() throws SecurityException, NoSuchFieldException {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		Field field = UserDaoMysqlImpl.class.getDeclaredField("username");
		FieldUtil.setFieldValue(userDao, field, "hctan");
		Assert.assertEquals("hctan", FieldUtil.getFieldValue(userDao, "username"));

	}

	@Test
	public void getField() throws SecurityException, NoSuchFieldException {
		{
			UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
			Field field = UserDaoMysqlImpl.class.getDeclaredField("yyuid");
			FieldUtil.setFieldValue(userDao, field, 1L);
			Assert.assertEquals(1L, FieldUtil.getFieldValue(userDao, Long.class));
		}
		{
			UserDaoEmptyImpl userDao = new UserDaoEmptyImpl();
			Assert.assertNull(FieldUtil.getField(userDao, Long.class));
		}

	}

	@Test
	public void getFieldValue() {
		{
			UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
			try {
				FieldUtil.getFieldValue(userDao, "test");
				Assert.fail("怎么没有抛NoSuchFieldRuntimeException?");
			}
			catch (NoSuchFieldRuntimeException e) {

			}
		}
		{
			UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
			try {
				FieldUtil.getFieldValue(userDao, Date.class);
				Assert.fail("怎么没有抛NoSuchFieldRuntimeException?");
			}
			catch (NoSuchFieldRuntimeException e) {

			}
		}
	}

}