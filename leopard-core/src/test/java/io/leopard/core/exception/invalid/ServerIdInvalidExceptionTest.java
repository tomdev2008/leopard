package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class ServerIdInvalidExceptionTest {

	@Test
	public void ServerIdInvalidException() {
		Assert.assertEquals("非法服务器ID[s1].", new ServerIdInvalidException("s1").getMessage());
	}

}