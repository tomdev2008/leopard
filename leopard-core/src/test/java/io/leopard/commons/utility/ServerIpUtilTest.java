package io.leopard.commons.utility;

import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Test;

public class ServerIpUtilTest {

	@Test
	public void getServerIp() {
		if (SystemUtils.IS_OS_WINDOWS) {
			Assert.assertEquals("127.0.0.1", ServerIpUtil.getServerIp());
		}
		else if (SystemUtils.IS_OS_LINUX) {
			ServerIpUtil.getServerIp();
		}
	}

	@Test
	public void ServerIpUtil() {
		new ServerIpUtil();
	}

}