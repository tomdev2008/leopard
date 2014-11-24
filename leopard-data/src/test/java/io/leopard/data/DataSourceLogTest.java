package io.leopard.data;

import org.junit.Test;

public class DataSourceLogTest {

	@Test
	public void DataSourceLog() {
		new DataSourceLog();
	}

	@Test
	public void debug() {
		DataSourceLog.debug("type", "message");
	}

}