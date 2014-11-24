package io.leopard.data.pub;

import io.leopard.data4j.redis.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PublisherTest {

	@Test
	public void Publisher() {
		new Publisher();
	}

	@Test
	public void listen() {
		Redis redis = Mockito.mock(Redis.class);
		IPubSub pub = Mockito.mock(IPubSub.class);
		Publisher.listen(pub, redis);
		try {
			Publisher.listen(pub, redis);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}

		Publisher.publish(pub, "message");
	}

}