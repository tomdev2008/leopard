package io.leopard.test.mock.runner;

import org.junit.Test;

public class MockRunnerTest {

	@Test
	public void MockRunner() throws Exception {
		new MockRunner(MockRunnerTest.class);
	}

}