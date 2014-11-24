package io.leopard.monitor.thread;

import org.junit.Test;

public class ThreadMonitorDaoImplTest {

	protected ThreadMonitorDaoImpl threadMonitorDaoImpl = new ThreadMonitorDaoImpl();

	@Test
	public void listAll() {
		threadMonitorDaoImpl.listAll();
	}

}