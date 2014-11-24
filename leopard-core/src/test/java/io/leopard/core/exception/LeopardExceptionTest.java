package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class LeopardExceptionTest {

	@Test
	public void LeopardException() {
		Assert.assertNull(new LeopardException().getMessage());
		Assert.assertEquals("msg", new LeopardException("msg").getMessage());
		Assert.assertEquals("msg", new LeopardException(new Exception("msg")).getMessage());
	}

}