package io.leopard.test.mock.runner;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.mockito.Mockito;

public class StandardApiRunnerTest {

	@Test
	public void test() {

	}

	@Test
	public void runChild() throws InitializationError, SecurityException, NoSuchMethodException {
		StandardApiRunner runner = Mockito.spy(new StandardApiRunner(StandardApiRunnerTest.class));

		FrameworkMethod frameworkMethod = Mockito.mock(FrameworkMethod.class);

		Method method = StandardApiRunnerTest.class.getDeclaredMethod("test");
		Mockito.doReturn(method).when(frameworkMethod).getMethod();
		RunNotifier notifier = Mockito.mock(RunNotifier.class);
		runner.runChild(frameworkMethod, notifier);
		Mockito.doReturn(true).when(runner).isOnlyApi();
		runner.runChild(frameworkMethod, notifier);
		Mockito.doReturn(true).when(runner).isStandardApiName(Mockito.anyString());
		runner.runChild(frameworkMethod, notifier);

	}

	@Test
	public void isOnlyApi() throws InitializationError {
		{
			StandardApiRunner runner = new StandardApiRunner(StandardApiRunnerTest.class);
			Assert.assertFalse(runner.isOnlyApi());
			Assert.assertFalse(runner.isOnlyApi());
		}
		System.setProperty("api", "true");
		Assert.assertTrue(new StandardApiRunner(StandardApiRunnerTest.class).isOnlyApi());
	}

	@Test
	public void isStandardApiName() throws InitializationError {
		StandardApiRunner runner = new StandardApiRunner(StandardApiRunnerTest.class);
		Assert.assertTrue(runner.isStandardApiName("add"));
		Assert.assertFalse(runner.isStandardApiName("other"));
	}

}