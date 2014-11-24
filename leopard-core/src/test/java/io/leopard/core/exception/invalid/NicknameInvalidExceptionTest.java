package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class NicknameInvalidExceptionTest {

	@Test
	public void NicknameInvalidException() {
		Assert.assertEquals("非法昵称[nick].", new NicknameInvalidException("nick").getMessage());
	}

}