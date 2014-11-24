package io.leopard.test.mock.reflect;

import java.lang.reflect.Method;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;


public class DaoMethodUtilTest {

	@Test
	public void DaoMethodUtil() {
		new DaoMethodUtil();
	}

	public static class UserDaoMysqlImpl {
		public boolean delete(String username, String opusername, Date lmodify) {
			return false;
		}

	}

	public static class UserServiceImpl {
		public boolean delete(String username, String opusername) {
			return false;
		}

		public String getNickname(String username) {
			return null;
		}
	}

	@Test
	public void getDaoMethod() {
		UserDaoMysqlImpl userDao = new UserDaoMysqlImpl();
		{
			Method method = MethodUtil.getMethod(UserServiceImpl.class, "getNickname");
			Object[] objs = DaoMethodUtil.getDaoMethod(userDao, method, new Object[] { "hctan" });
			Assert.assertNull(objs);
		}
		{
			Method method = MethodUtil.getMethod(UserServiceImpl.class, "delete");
			Object[] objs = DaoMethodUtil.getDaoMethod(userDao, method, new Object[] { "hctan", "hctan" });
			Assert.assertNotNull(objs);
		}
	}

}