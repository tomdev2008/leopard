package io.leopard.test.mock;

import io.leopard.command.IContextFactory;

public class CommandMockContextFactory implements IContextFactory {

	protected static class TestObject {

	}

	@Override
	public <E> E getBean(Class<E> c) {
		System.err.println("getBean:" + c);
		return Mock.spy(new TestObject(), c);
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
