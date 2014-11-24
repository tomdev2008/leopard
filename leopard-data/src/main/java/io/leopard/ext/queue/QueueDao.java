package io.leopard.ext.queue;

public interface QueueDao {

	int add(Object obj);

	int count();

	String pop();

}
