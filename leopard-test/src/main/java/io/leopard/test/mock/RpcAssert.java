package io.leopard.test.mock;

import io.leopard.test.mock.proxy.Proxy;
import io.leopard.test.mock.proxy.RpcMethodHandlerAssertWhenImpl;

public class RpcAssert {

	public static interface AssertStubber {
		<T> T when(T mock);
	}

	public static AssertStubber doReturn(final Object toBeReturned) {
		return new AssertStubber() {
			@Override
			public <T> T when(T mock) {
				// System.out.println("mock:" + mock);
				T proxy = Proxy.newProxyInstance(mock, new RpcMethodHandlerAssertWhenImpl(mock, toBeReturned));
				return proxy;
			}
		};
	}
}
