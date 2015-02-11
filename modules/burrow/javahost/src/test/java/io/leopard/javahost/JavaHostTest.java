package io.leopard.javahost;

import org.junit.Assert;
import org.junit.Test;

public class JavaHostTest {

	@Test
	public void queryForIp() {
		Assert.assertEquals("112.126.75.27", JavaHost.queryForIp("leopard2e.leopard.io"));
	}

}