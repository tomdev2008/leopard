package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class UsernameInvalidExceptionTest {

	@Test
	public void UsernameInvalidException() {
		Assert.assertEquals("非法用户名[username].", new UsernameInvalidException("username").getMessage());
		Assert.assertEquals("msg", new UsernameInvalidException("username", "msg").getMessage());
	}

}