package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class StatusCodeExceptionTest {

	@Test
	public void StatusCodeException() {
		StatusCodeException e = new StatusCodeException("-100", "msg", "msg");
		Assert.assertEquals("msg", e.getMessage());
		Assert.assertEquals("-100", e.getStatus());
	}

}