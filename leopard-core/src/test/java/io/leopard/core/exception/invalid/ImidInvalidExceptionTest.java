package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class ImidInvalidExceptionTest {

	@Test
	public void ImidInvalidException() {
		Assert.assertEquals("非法YY号[0].", new ImidInvalidException(0L).getMessage());
	}

}