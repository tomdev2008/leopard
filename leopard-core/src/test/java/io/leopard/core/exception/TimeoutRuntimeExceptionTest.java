package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class TimeoutRuntimeExceptionTest {

	@Test
	public void TimeoutRuntimeException() {
		RuntimeException e1 = new RuntimeException("error1");
		TimeoutRuntimeException e = new TimeoutRuntimeException("msg", e1);
		Assert.assertEquals("msg", e.getMessage());
	}

}