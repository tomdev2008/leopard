package io.leopard.test.mock.runner;

import io.leopard.test.mock.Mock;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 只在开发环境运行的测试代码.
 * 
 * @author 阿海
 * 
 */
public class DevRunner extends SpringJUnit4ClassRunner {

	public DevRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	public void run(RunNotifier notifier) {
		// if (true) {
		// new Exception().printStackTrace();
		// return;
		// }
		boolean isEclipse = this.isEclipse();
		System.out.println("isEclipse:" + isEclipse);
		if (isEclipse && Mock.getSpyCount() == 0) {
			// eclipse环境
			super.run(notifier);
			return;
		}
		else {
			// 非eclipse环境
			TestClass testClass = super.getTestClass();
			String className = testClass.getJavaClass().getName();
			System.out.println("该类只在eclipse环境下运行,className:" + className);
		}
	}

	protected boolean isEclipse() {
		StackTraceElement[] stacks = new Exception().getStackTrace();
		String mainClassName = stacks[stacks.length - 1].getClassName();
		return (mainClassName.startsWith("org.eclipse.jdt"));
	}
}
