package io.leopard.data.pub;

import io.leopard.data4j.redis.Redis;

import org.junit.Test;
import org.mockito.Mockito;

public class PubSubRsyncImplTest {

	@Test
	public void PubSubRsyncImpl() {
		Redis redis = Mockito.mock(Redis.class);

		PubSubRsyncImpl pub = new PubSubRsyncImpl(redis, "channel") {
			@Override
			public void subscribe(String message, boolean isMySelf) {

			}
		};
		pub.init();
		pub.add("set", "key", "value", false);
		pub.destroy();
	}
}