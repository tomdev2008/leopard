package io.leopard.monitor.thread;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class ThreadInfoTest {

	@Test
	public void ThreadInfo() {
		BeanAssert.assertModel(ThreadInfo.class);
	}

}