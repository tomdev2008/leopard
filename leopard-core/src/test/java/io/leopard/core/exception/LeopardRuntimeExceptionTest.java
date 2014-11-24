package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class LeopardRuntimeExceptionTest {

	@Test
	public void DuowanRuntimeException() {
		Assert.assertEquals("msg", new LeopardRuntimeException("msg").getMessage());
		Assert.assertEquals("msg1", new LeopardRuntimeException(new Exception("msg1")).getMessage());
	}

}