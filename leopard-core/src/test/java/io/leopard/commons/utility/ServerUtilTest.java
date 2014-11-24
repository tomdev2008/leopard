package io.leopard.commons.utility;

import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Test;

public class ServerUtilTest {

	@Test
	public void getServerIp() {
		if (SystemUtils.IS_OS_WINDOWS) {
			Assert.assertEquals("127.0.0.1", ServerUtil.getServerIp());
		}
		else if (SystemUtils.IS_OS_LINUX) {
			ServerUtil.getServerIp();
		}
	}

	// @Test
	// public void getIp() {
	// if (SystemUtils.IS_OS_LINUX) {
	// String[] prefixs = { "172.17.1." };
	// String ip = ServerUtil.getIp(prefixs);
	// Assert.assertEquals("172.17.1.254", ip);
	// }
	// }

	@Test
	public void ServerUtil() {
		new ServerUtil();
	}

}