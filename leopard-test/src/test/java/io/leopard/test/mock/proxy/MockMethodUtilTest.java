package io.leopard.test.mock.proxy;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MockMethodUtilTest {

	public static class UserDaoMysql {

	}

	@Test
	public void MockMethodUtil() {
		new MockMethodUtil();
	}

	@Test
	public void invokeMethod() {

	}

	@Test
	public void toAnyArgs() {
		Class<?>[] params = new Class<?>[] { String.class, int.class, long.class, boolean.class, float.class, double.class, Date.class, List.class, HashSet.class, MockMethodUtil.class };
		Assert.assertNotNull(MockMethodUtil.toAnyArgs(params));
	}
}