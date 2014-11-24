package io.leopard.test.mock.stubbing;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

public class AssertMethodHandlerTest {
	public static class MethodHandlerImpl extends AssertMethodHandler {

		@Override
		public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {

			return null;
		}

	}

	@Test
	public void setMock() {
		MethodHandlerImpl methodHandler = new MethodHandlerImpl();
		methodHandler.setMock("str");
		Assert.assertNotNull(methodHandler.getMock());
	}

}