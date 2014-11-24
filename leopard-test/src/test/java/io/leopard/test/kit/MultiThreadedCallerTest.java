package io.leopard.test.kit;

import io.leopard.core.Invoker;

import org.junit.Test;

public class MultiThreadedCallerTest {

	@Test
	public void execute() {
		MultiThreadedCaller caller = new MultiThreadedCaller(2);
		caller.add(new Invoker() {
			@Override
			public void execute() {

			}

		});
		caller.execute();
	}

}