package io.leopard.monitor.connection;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class JdbcConnectionListenerImplTest {

	@Test
	public void open() {
		JdbcConnectionListenerImpl listener = new JdbcConnectionListenerImpl();
		listener.setPoolConfig("host", 3306, 10, 15, "database");
		Connection connection = Mockito.mock(Connection.class);
		listener.open(connection, System.nanoTime());
		Assert.assertEquals(1, listener.connectionInfo.getCurrentSize());

		listener.close(connection);
		Assert.assertEquals(0, listener.connectionInfo.getCurrentSize());

		{
			listener.open(null, System.nanoTime());
			Assert.assertEquals(1, listener.connectionInfo.getCurrentSize());

			listener.broken();
			Assert.assertEquals(1, listener.connectionInfo.getBrokenCount());
		}
	}

}