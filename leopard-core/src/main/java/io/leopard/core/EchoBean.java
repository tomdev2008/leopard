package io.leopard.core;

import javax.annotation.PostConstruct;

public class EchoBean {

	public EchoBean() {
		System.err.println("echo bean:");
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@PostConstruct
	public void init() {
		System.err.println("echo bean:" + this.message);
	}

}
