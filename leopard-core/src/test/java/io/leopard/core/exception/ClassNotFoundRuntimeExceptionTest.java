package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class ClassNotFoundRuntimeExceptionTest {

	@Test
	public void ClassNotFoundRuntimeException() {
		Assert.assertEquals("msg", new ClassNotFoundRuntimeException("msg").getMessage());

		Assert.assertEquals("msg", new ClassNotFoundRuntimeException("msg", new Exception()).getMessage());
	}

}