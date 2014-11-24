package io.leopard.monitor.connection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import redis.clients.jedis.Jedis;

public class RedisConnectionListenerImplTest {

	@Test
	public void open() {
		RedisConnectionListenerImpl listener = new RedisConnectionListenerImpl();
		listener.setPoolConfig("host", 6311, 10, 10, null);
		Jedis resource = Mockito.mock(Jedis.class);
		listener.open(resource, System.nanoTime());
		Assert.assertEquals(1, listener.connectionInfo.getCurrentSize());

		listener.close(resource);
		Assert.assertEquals(0, listener.connectionInfo.getCurrentSize());

		{
			listener.open(null, System.nanoTime());
			Assert.assertEquals(1, listener.connectionInfo.getCurrentSize());

			listener.broken();
			Assert.assertEquals(1, listener.connectionInfo.getBrokenCount());
		}
	}

}