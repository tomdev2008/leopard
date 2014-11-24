package io.leopard.test.mock;

import io.leopard.burrow.timer.Timer;


public class TimerAssert {

	public static void assertDefault(Timer timer) {
		timer.isEnabled();
		Assert.assertTrue(timer.getThreadCount() > 0);
		Assert.assertNotNull(timer.getPeriod());
	}
}
