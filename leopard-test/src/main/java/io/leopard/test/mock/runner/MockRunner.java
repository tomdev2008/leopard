package io.leopard.test.mock.runner;

import org.powermock.modules.junit4.PowerMockRunner;

public class MockRunner extends PowerMockRunner {

	public MockRunner(Class<?> clazz) throws Exception {
		super(init(clazz));
	}

	protected static Class<?> init(Class<?> clazz) {
		return clazz;
	}

	// @Override
	// public void run(RunNotifier notifier) {
	// // System.out.println("run:" + notifier);
	// super.run(notifier);
	// }

}
