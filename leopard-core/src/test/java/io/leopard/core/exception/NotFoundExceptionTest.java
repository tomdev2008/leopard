package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class NotFoundExceptionTest {

	@Test
	public void NotFoundException() {
		Assert.assertEquals("msg", new NotFoundException("msg").getMessage());
	}

}