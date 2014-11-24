package io.leopard.core.exception.exist;

import org.junit.Assert;
import org.junit.Test;

public class UserExistExceptionTest {

	@Test
	public void UserExistException() {
		Assert.assertEquals("用户[user]已存在.", new UserExistException("user").getMessage());
	}

}