package io.leopard.core.exception.notfound;

import org.junit.Assert;
import org.junit.Test;

public class UserNotFoundExceptionTest {

	@Test
	public void UserNotFoundException() {
		Assert.assertEquals("用户[username]不存在.", new UserNotFoundException("username").getMessage());
		Assert.assertEquals("msg", new UserNotFoundException("username", "msg").getMessage());
	}
}