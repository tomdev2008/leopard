package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class SignatureInvalidExceptionTest {

	@Test
	public void SignatureInvalidException() {
		Assert.assertEquals("msg", new SignatureInvalidException("msg").getMessage());
	}

}