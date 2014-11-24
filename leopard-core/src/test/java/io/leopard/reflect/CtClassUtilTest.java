package io.leopard.reflect;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class CtClassUtilTest {

	public static class UserService {

		public boolean add(String username, String nickname) {
			return true;
		}
	}

	@Test
	public void CtClassUtil() {
		new CtClassUtil();
	}

	@Test
	public void getClass2() {
		CtClassUtil.getClass(UserService.class);
	}

	@Test
	public void getParameterNames() throws SecurityException, NoSuchMethodException {
		Method method = UserService.class.getMethod("add", String.class, String.class);
		String[] names = CtClassUtil.getParameterNames(method);
		Assert.assertEquals("username, nickname", StringUtils.join(names, ", "));
	}

}