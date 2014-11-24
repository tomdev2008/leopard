package io.leopard.test.mock.stubbing;

public interface AssertStubber {

	<T> T when(T mock);

	<T, DAO> T when(T mock, DAO daoMock);

}
