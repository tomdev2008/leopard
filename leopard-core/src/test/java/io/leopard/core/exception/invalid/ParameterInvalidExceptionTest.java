package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class ParameterInvalidExceptionTest {

	@Test
	public void ParameterInvalidException() {
		Assert.assertEquals("msg", new ParameterInvalidException("msg").getMessage());
	}

}