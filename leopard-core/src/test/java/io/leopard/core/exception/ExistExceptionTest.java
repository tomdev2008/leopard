package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class ExistExceptionTest {

	@Test
	public void ExistException() {
		Assert.assertEquals("msg", new ExistException("msg").getMessage());
	}

}