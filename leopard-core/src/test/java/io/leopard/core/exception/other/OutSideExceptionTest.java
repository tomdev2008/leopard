package io.leopard.core.exception.other;

import org.junit.Assert;
import org.junit.Test;

public class OutSideExceptionTest {

	@Test
	public void OutSideException() {
		Assert.assertEquals("msg", new OutSideException("msg").getMessage());
		Assert.assertEquals("msg", new OutSideException("msg", new Exception()).getMessage());
	}

}