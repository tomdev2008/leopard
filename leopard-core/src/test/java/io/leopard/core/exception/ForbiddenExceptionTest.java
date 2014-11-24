package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class ForbiddenExceptionTest {

	@Test
	public void ForbiddenException() {
		Assert.assertEquals("msg", new ForbiddenException("msg").getMessage());
	}

}