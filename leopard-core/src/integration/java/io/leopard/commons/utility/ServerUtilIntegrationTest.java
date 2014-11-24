package io.leopard.commons.utility;

import io.leopard.commons.utility.ServerUtil;

import org.junit.Test;

public class ServerUtilIntegrationTest {

	@Test
	public void getServerIp() {
		String ip = ServerUtil.getServerIp();
		System.out.println("ip:" + ip);
	}

}