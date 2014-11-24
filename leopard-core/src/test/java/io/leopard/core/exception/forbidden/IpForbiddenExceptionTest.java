package io.leopard.core.exception.forbidden;

import org.junit.Assert;
import org.junit.Test;

public class IpForbiddenExceptionTest {

	@Test
	public void IpForbiddenException() {
		Assert.assertEquals("IP[127.0.0.1]没有访问权限.", new IpForbiddenException("127.0.0.1").getMessage());
		Assert.assertEquals("msg", new IpForbiddenException("127.0.0.1", "msg").getMessage());
	}

}