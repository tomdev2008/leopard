package io.leopard.data.queue;

public interface Queue {

	void publish(String key, String message);

	String consume(String key, IConsumer callback);

}
