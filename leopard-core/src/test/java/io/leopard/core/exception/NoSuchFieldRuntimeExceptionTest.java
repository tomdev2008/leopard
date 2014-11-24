package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class NoSuchFieldRuntimeExceptionTest {

	@Test
	public void NoSuchFieldRuntimeException() {

		Assert.assertEquals("msg", new NoSuchFieldRuntimeException("msg").getMessage());

		Assert.assertEquals("msg", new NoSuchFieldRuntimeException("msg", new Exception()).getMessage());

	}
}