package io.leopard.javahost;

import org.junit.Assert;
import org.junit.Test;

public class JavaHostTest {

	@Test
	public void queryForIp() {
		Assert.assertEquals("112.126.75.27", JavaHost.queryForIp("leopard2e.leopard.io"));
	}

	@Test
	public void isValidIp() {
		// Assert.assertTrue(JavaHost.isValidIp("255.255.255.255"));
		// Assert.assertFalse(JavaHost.isValidIp("255.255.255.256"));
		// Assert.assertFalse(JavaHost.isValidIp("255.255.255"));
		Assert.assertFalse(JavaHost.isValidIp("255.255.255."));
	}

}