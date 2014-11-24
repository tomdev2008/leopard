package io.leopard.monitor.connection;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionInfoTest {

	@Test
	public void ConnectionInfo() {
		BeanAssert.assertModel(ConnectionInfo.class);
	}

	@Test
	public void getTotalTimeStr() {
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setTotalTime(1000 * 1000 * 1);
		Assert.assertEquals("1ms", connectionInfo.getTotalTimeStr());
		connectionInfo.setTotalTime(1000 * 1000 * 1000 * 1);
		Assert.assertEquals("1s", connectionInfo.getTotalTimeStr());
	}

}