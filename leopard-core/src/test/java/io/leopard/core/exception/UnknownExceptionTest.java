package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class UnknownExceptionTest {

	@Test
	public void UnknownException() {
		Assert.assertEquals("未知异常.", new UnknownException().getMessage());
		Assert.assertEquals("msg", new UnknownException("msg").getMessage());
	}

}