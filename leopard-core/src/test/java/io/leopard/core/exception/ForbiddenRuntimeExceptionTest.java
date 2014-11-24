package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class ForbiddenRuntimeExceptionTest {

	@Test
	public void ForbiddenRuntimeException() {
		Assert.assertEquals("msg", new ForbiddenRuntimeException("msg").getMessage());
	}

}