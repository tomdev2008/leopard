package io.leopard.core.exception.other;

import org.junit.Assert;
import org.junit.Test;

public class OtherExceptionTest {

	@Test
	public void OtherException() {
		Assert.assertEquals("msg", new OtherException("msg").getMessage());
		Assert.assertEquals("msg", new OtherException("msg", new Exception()).getMessage());
	}

}