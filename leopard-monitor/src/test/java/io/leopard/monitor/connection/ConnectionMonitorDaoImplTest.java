package io.leopard.monitor.connection;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionMonitorDaoImplTest {

	@Test
	public void add() {
		ConnectionMonitorDaoImpl connectionMonitorDaoImpl = new ConnectionMonitorDaoImpl();

		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setTotalTime(1);
		connectionInfo.setContent("name1");
		connectionMonitorDaoImpl.add(connectionInfo);

		Assert.assertEquals(1, connectionMonitorDaoImpl.listAll().size());
	}

}