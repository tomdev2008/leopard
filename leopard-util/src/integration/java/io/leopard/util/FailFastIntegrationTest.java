package io.leopard.util;

import io.leopard.commons.utility.SystemUtil;
import io.leopard.core.exception.TimeoutRuntimeException;
import io.leopard.util.FailFast;

import org.junit.Test;

public class FailFastIntegrationTest {

	@Test
	public void execute() {
		try {
			test();
		}
		catch (TimeoutRuntimeException e) {
			e.printStackTrace();
		}
		System.err.println("stop");
		SystemUtil.sleep(1000 * 5);
		System.err.println("end");
	}

	@Test
	public void test() {
		String result = new FailFast<String>() {
			@Override
			public String call() throws Exception {
				return post();
			}

		}.execute(2100);
		System.err.println("result:" + result);
	}

	protected String post() {
		try {
			Thread.sleep(1000 * 2);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		System.err.println("post");
		return "post";
	}

	@Test
	public void FailFast() {
	//AUTO
	}

}