package io.leopard.util;

import io.leopard.core.Invoker;
import io.leopard.util.Caller;

import org.junit.Test;

public class CallerTest {

	protected Caller caller = new Caller();

	@Test
	public void execute() {
		caller.add(new Invoker() {
			@Override
			public void execute() {

			}
		});

		caller.execute();
	}
}