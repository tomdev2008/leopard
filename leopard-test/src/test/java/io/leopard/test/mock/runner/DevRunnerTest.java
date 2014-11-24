package io.leopard.test.mock.runner;

import io.leopard.commons.utility.SystemUtil;
import io.leopard.test.mock.Mock;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.mockito.Mockito;

public class DevRunnerTest {

	@Test
	public void test() {

	}

	//
	@Test
	public void isEclipse() throws InitializationError {
		DevRunner runner = new DevRunner(DevRunnerTest.class);
		if (SystemUtil.isWindows()) {
			Assert.assertTrue(runner.isEclipse());
		}
		else {
			Assert.assertFalse(runner.isEclipse());
		}
	}

	@Test
	public void run() throws InitializationError, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		RunNotifier notifier = Mockito.mock(RunNotifier.class);
		DevRunner runner = Mockito.spy(new DevRunner(DevRunnerTest.class));
		Mockito.doReturn(false).when(runner).isEclipse();
		runner.run(notifier);

		Mockito.doReturn(true).when(runner).isEclipse();
		runner.run(notifier);

		{
			Field filed = Mock.class.getDeclaredField("spyCount");
			filed.setAccessible(true);
			filed.set(null, 1);
			runner.run(notifier);
		}
	}

}