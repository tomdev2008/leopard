package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class YyuidInvalidExceptionTest {

	@Test
	public void YyuidInvalidException() {
		Assert.assertEquals("非法yyuid[1].", new UidInvalidException(1L).getMessage());
	}

}