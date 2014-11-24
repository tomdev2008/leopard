package io.leopard.core.exception.other;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionRefusedExceptionTest {

	@Test
	public void ConnectionRefusedException() {
		Assert.assertEquals("msg", new ConnectionRefusedException("msg").getMessage());
	}

}