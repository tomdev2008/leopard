package io.leopard.data.queue;

import io.leopard.burrow.timer.SimpleTimer;
import io.leopard.data4j.redis.RedisImpl;

public class QueueRedisImpl extends AbstractQueue implements Queue {

	private RedisImpl redis;

	@Override
	public void init() {
		redis = new RedisImpl(server, 16, 1000 * 10);
		redis.init();
	}

	@Override
	public void destroy() {
		super.destroy();
		redis.destroy();
	}

	@Override
	public void publish(String routingKey, String message) {
		redis.rpush(routingKey, message);
	}

	@Override
	public String consume(final String queue, final IConsumer callback) {
		new SimpleTimer(1) {
			@Override
			public void start() {
				while (true) {
					String message = redis.lpop(queue);
					if (message == null) {
						break;
					}
					try {
						callback.consume(message);
					}
					catch (Exception e) {
						logger.error("message:" + message);
						logger.error(e.getMessage(), e);
					}
				}
			}
		}.run();
		return "OK";
	}

}
