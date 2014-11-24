package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class IpInvalidExceptionTest {

	@Test
	public void IpInvalidException() {
		Assert.assertEquals("非法IP[127.0.0.1].", new IpInvalidException("127.0.0.1").getMessage());
		Assert.assertEquals("msg", new IpInvalidException("127.0.0.1", "msg").getMessage());
	}

}