package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class PassportInvalidExceptionTest {

	@Test
	public void PassportInvalidException() {
		Assert.assertEquals("非法用户名[passport].", new PassportInvalidException("passport").getMessage());
		Assert.assertEquals("msg", new PassportInvalidException("passport", "msg").getMessage());
	}

}