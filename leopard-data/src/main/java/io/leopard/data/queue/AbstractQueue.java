package io.leopard.data.queue;

import io.leopard.core.context.ContextImpl;

public abstract class AbstractQueue extends ContextImpl implements Queue {
	protected String server;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
}
