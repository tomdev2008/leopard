package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class NotFoundRuntimeExceptionTest {

	@Test
	public void NotFoundRuntimeException() {

		Assert.assertEquals("msg", new NotFoundRuntimeException("msg").getMessage());

		Assert.assertEquals("msg", new NotFoundRuntimeException("msg", new Exception()).getMessage());

	}

}