package io.leopard.test.mock;

import org.junit.Test;

public class CommandMockContextFactoryTest {

	public static class UserDao {

	}

	@Test
	public void CommandMockContextFactory() {
		CommandMockContextFactory factory = new CommandMockContextFactory();
		Assert.assertNull(factory.getBean(""));
		factory.exit();
		factory.shutDown();
		factory.getBean(UserDao.class);
	}

}