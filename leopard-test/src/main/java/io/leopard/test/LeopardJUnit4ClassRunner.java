package io.leopard.test;

import io.leopard.test.annotation.NoLog;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class LeopardJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	public LeopardJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

		System.out.println("LeopardJUnit4ClassRunner new ");
		// TestContextLoader
	}

	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		super.collectInitializationErrors(errors);

		TestClass testClass = super.getTestClass();
		NoLog anno = testClass.getJavaClass().getAnnotation(NoLog.class);

		if (anno == null) {
			return;
		}

		String config = "log4j.rootLogger=INFO, stdout\n";
		config += "log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n";
		config += "log4j.appender.stdout.Threshold=FATAL\n";
		config += "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n";
		config += "log4j.appender.stdout.layout.ConversionPattern=%d %p [%x,%t] - [%c] - %m%n\n";

		InputStream input = new ByteArrayInputStream(config.getBytes());
		PropertyConfigurator.configure(input);
	}

	@Override
	protected void validateTestMethods(List<Throwable> errors) {
		// 忽略测试代码合法性检查
		// super.validateTestMethods(errors);
	}

	@Override
	protected Statement methodInvoker(FrameworkMethod method, Object test) {
		return new SmartParameterInvokeMethod(method, test);
	}

}
