package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class InvalidExceptionTest {

	@Test
	public void InvalidException() {
		Assert.assertEquals("msg", new InvalidException("msg").getMessage());
	}

}