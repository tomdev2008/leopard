package io.leopard.test.mock.stubbing;

import javassist.util.proxy.MethodHandler;

public abstract class AssertMethodHandler implements MethodHandler {

	private Object mock;

	public Object getMock() {
		return mock;
	}

	public void setMock(Object mock) {
		this.mock = mock;
	}

}
