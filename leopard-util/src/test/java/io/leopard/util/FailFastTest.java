package io.leopard.util;

import io.leopard.core.exception.TimeoutRuntimeException;

import org.junit.Test;

public class FailFastTest {

	@Test
	public void execute() {
		try {
			test();
		}
		catch (TimeoutRuntimeException e) {
			e.printStackTrace();
		}
		System.err.println("stop");
	}

	@Test
	public void test() {
		String result = new FailFast<String>() {
			@Override
			public String call() throws Exception {
				return post();
			}

		}.execute(210);
		System.err.println("result:" + result);
	}

	protected String post() {
		try {
			Thread.sleep(100 * 2);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		System.err.println("post");
		return "post";
	}

}