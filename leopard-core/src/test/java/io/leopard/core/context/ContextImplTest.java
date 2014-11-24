package io.leopard.core.context;

import org.junit.Test;

public class ContextImplTest {

	protected ContextImpl contextImpl = new ContextImpl();

	public static interface UserDao {

	}

	public static class UserDaoMysqlImpl implements UserDao {

	}

	protected UserDao userDaoMysqlImpl;

	@Test
	public void destroy() {
		contextImpl.destroy();
	}

	@Test
	public void init() {
		contextImpl.init();
	}

	// @Test
	// public void checkDaoName() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	// ContextImplTest contextImplTest = new ContextImplTest();
	// Field field = ContextImplTest.class.getDeclaredField("userDaoMysqlImpl");
	// try {
	// contextImpl.checkDaoName(contextImplTest, field);
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (NullPointerException e) {
	//
	// }
	//
	// contextImplTest.userDaoMysqlImpl = new UserDaoMysqlImpl();
	//
	// contextImpl.checkDaoName(contextImplTest, field);
	// }

}