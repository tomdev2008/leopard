package io.leopard.monitor.connection;

import io.leopard.test.mock.Mock;
import io.leopard.test.mock.MockTests;

import org.junit.Test;

public class ConnectionMonitorServiceImplTest extends MockTests {

	protected ConnectionMonitorServiceImpl connectionMonitorService = Mock.spy(this, ConnectionMonitorServiceImpl.class);

	@Test
	public void listAll() {
		connectionMonitorService.listAll();
	}

}