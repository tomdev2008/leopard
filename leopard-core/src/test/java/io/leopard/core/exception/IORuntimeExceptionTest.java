package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class IORuntimeExceptionTest {

	@Test
	public void IORuntimeException() {
		RuntimeException e1 = new RuntimeException("error1");
		IORuntimeException e = new IORuntimeException("msg", e1);
		Assert.assertEquals("msg", e.getMessage());
	}

}