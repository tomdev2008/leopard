package io.leopard.commons.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

public class ClockTest {
	private Log logger = LogFactory.getLog(ClockTest.class);

	@Test
	public void start() {
		Clock clock = Clock.start();
		long time = clock.time();
		System.out.println("time:" + time);
	}

	@Test
	public void start2() {
		Clock clock = Clock.start(10);
		long time = clock.time();
		System.out.println("time:" + time);
	}

	@Test
	public void size() {
		Clock clock = Clock.start(10);
		Assert.assertEquals(10, clock.size());
	}

	@Test
	public void time() {
		Clock clock = Clock.start(10);
		clock.time("name");
		clock.time("");
		clock.time(logger, "name");
		clock.time(logger, "");
		clock.time(logger, "name", 1);
		clock.time(logger, "name", 0);
		clock.time(logger, "", 0);
	}

	@Test
	public void log() {
		Clock clock = Clock.start(10);
		clock.log(logger, "message");
		Clock.log(logger, "message", 1);
	}

	@Test
	public void debug() {
		Clock.debug(logger, "message", 1);
	}

	@Test
	public void avg() throws InterruptedException {
		Clock clock = Clock.start(10);
		Thread.sleep(10);
		clock.time();
		clock.avg();
		Thread.sleep(10);
		clock.avg("message");
		Thread.sleep(10);
		clock.avg(1, "message");
	}

	@Test
	public void getLevel() {
		Assert.assertEquals(1, Clock.getLevel(1));
		Assert.assertEquals(2, Clock.getLevel(2));
		Assert.assertEquals(2, Clock.getLevel(9));
		Assert.assertEquals(3, Clock.getLevel(10));
		Assert.assertEquals(3, Clock.getLevel(49));
		Assert.assertEquals(4, Clock.getLevel(50));
		Assert.assertEquals(4, Clock.getLevel(99));
		Assert.assertEquals(5, Clock.getLevel(100));
		Assert.assertEquals(5, Clock.getLevel(499));
		Assert.assertEquals(6, Clock.getLevel(500));
		Assert.assertEquals(6, Clock.getLevel(999));
		Assert.assertEquals(9, Clock.getLevel(1000));
	}

	@Test
	public void Clock() {
		new Clock();
	}
}