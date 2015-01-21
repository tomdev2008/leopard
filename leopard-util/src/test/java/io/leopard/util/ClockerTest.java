package io.leopard.util;

import org.junit.Test;

public class ClockerTest {

	@Test
	public void start() {
		Clocker clocker = Clocker.start();

		clocker.log("url");
	}

}