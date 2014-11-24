package io.leopard.util;

import io.leopard.util.Clocker;

import org.junit.Test;

public class ClockerTest {

	@Test
	public void start() {
		Clocker clocker = Clocker.start();

		clocker.log("url");
	}

}