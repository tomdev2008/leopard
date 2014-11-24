package io.leopard.test.mock;

import io.leopard.burrow.timer.Period;
import io.leopard.burrow.timer.Timer;

import org.junit.Test;
import org.mockito.Mockito;

public class TimerAssertTest {

	@Test
	public void TimerAssert() {
		new TimerAssert();
	}

	@Test
	public void assertDefault() {
		Timer timer = Mockito.mock(Timer.class);
		Mockito.doReturn(1).when(timer).getThreadCount();
		Mockito.doReturn(Mockito.mock(Period.class)).when(timer).getPeriod();
		TimerAssert.assertDefault(timer);
	}

	@Test
	public void assertDefault2() {
		Timer timer = Mockito.mock(Timer.class);
		Mockito.doReturn(0).when(timer).getThreadCount();
		try {
			TimerAssert.assertDefault(timer);
			Assert.fail("怎么没有抛异常?");
		}
		catch (AssertionError e) {

		}
	}
}