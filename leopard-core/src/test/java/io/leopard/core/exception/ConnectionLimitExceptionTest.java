package io.leopard.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionLimitExceptionTest {

	@Test
	public void ConnectionLimitException() {
		Assert.assertEquals("您[user]访问[uri]太频繁了，歇一会儿吧.", new ConnectionLimitException("user", "uri").getMessage());
	}

}