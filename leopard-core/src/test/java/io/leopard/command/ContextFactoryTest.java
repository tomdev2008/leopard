package io.leopard.command;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LeopardMockRunner.class)
public class ContextFactoryTest {
	static {
		System.setProperty(ContextFactory.FACTORY_PROPERTYNAME, TestContextFactory.class.getName());
	}

	public static class TestContextFactory implements IContextFactory {

		@Override
		public <E> E getBean(Class<E> c) {
			return null;
		}

		@Override
		public Object getBean(String beanName) {
			return null;
		}

		@Override
		public void exit() {
		}

		@Override
		public void shutDown() {
		}

	}

	@Test
	public void shutDown() {
		ContextFactory.shutDown();
	}

	@Test
	public void ContextFactory() {
		new ContextFactory();
	}

	@Test
	public void getApplicationContext() throws Exception {

	}

	@Test
	public void getBean() {
		Assert.assertNull(ContextFactory.getBean("ok"));
		Assert.assertNull(ContextFactory.getBean(String.class));
	}

	@Test
	public void exit() {
		ContextFactory.exit();
	}

}