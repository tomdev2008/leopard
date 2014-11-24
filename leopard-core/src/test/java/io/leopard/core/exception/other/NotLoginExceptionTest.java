package io.leopard.core.exception.other;

import org.junit.Assert;
import org.junit.Test;

public class NotLoginExceptionTest {

	@Test
	public void NotLoginException() {
		Assert.assertEquals("msg", new NotLoginException("msg").getMessage());
	}

}