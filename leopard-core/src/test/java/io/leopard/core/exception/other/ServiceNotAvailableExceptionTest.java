package io.leopard.core.exception.other;

import org.junit.Assert;
import org.junit.Test;

public class ServiceNotAvailableExceptionTest {

	@Test
	public void ServiceNotAvailableException() {
		Assert.assertEquals("msg", new ServiceNotAvailableException("msg").getMessage());
	}

}