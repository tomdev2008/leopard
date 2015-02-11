package io.leopard.javahost.impl;

import io.leopard.javahost.JavaHost;

import org.junit.Assert;
import org.junit.Test;

public class DnsImplTest {

	private DnsImpl dns = new DnsImpl();

	@Test
	public void update() {
		Assert.assertNull(JavaHost.queryForIp("javahost.leopard.io"));
		dns.update("javahost.leopard.io", "127.0.0.1");
		Assert.assertEquals("127.0.0.1", JavaHost.queryForIp("javahost.leopard.io"));
		dns.remove("javahost.leopard.io");
		Assert.assertNull(JavaHost.queryForIp("javahost.leopard.io"));
	}

	@Test
	public void query() {

		{
			JavaHost.queryForIp("leopard.io");
			this.dns.query("leopard.io");
		}
		{
			dns.update("javahost.leopard.io", "127.0.0.1");
			JavaHost.queryForIp("javahost.leopard.io");
			this.dns.query("javahost.leopard.io");
		}
		{
			JavaHost.queryForIp("leopard2e.leopard.io");
			this.dns.query("leopard2e.leopard.io");
		}
	}

}