package io.leopard.util;

import io.leopard.core.Invoker;
import io.leopard.util.MultiThreadCaller;

import org.junit.Test;

public class MultiThreadCallerTest {

	@Test
	public void execute() {
		MultiThreadCaller caller = new MultiThreadCaller(1);
		caller.execute(new Invoker() {
			@Override
			public void execute() {

			}
		});
	}

	@Test
	public void execute2() {
		MultiThreadCaller caller = new MultiThreadCaller(2, 2);
		caller.execute(new Invoker() {
			@Override
			public void execute() {

			}
		});
	}
}