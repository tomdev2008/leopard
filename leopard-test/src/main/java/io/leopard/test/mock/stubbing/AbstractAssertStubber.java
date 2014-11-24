package io.leopard.test.mock.stubbing;

import io.leopard.test.mock.proxy.Proxy;
import javassist.util.proxy.MethodHandler;

public class AbstractAssertStubber implements AssertStubber {

	@Override
	public <T> T when(T mock) {
		return this.when(mock, null);
	}

	@Override
	public <T, DAO> T when(T mock, DAO daoMock) {
		return null;
	}

	protected <T> T invoke(T mock, MethodHandler methodHandler) {
		T proxy = Proxy.newProxyInstance(mock, methodHandler);
		return proxy;
	}

}
