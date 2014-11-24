package io.leopard.core.exception.other;

import org.junit.Assert;
import org.junit.Test;

public class RepeatSubmitExceptionTest {

	@Test
	public void RepeatSubmitException() {
		Assert.assertEquals("msg", new RepeatSubmitException("msg").getMessage());
	}

}