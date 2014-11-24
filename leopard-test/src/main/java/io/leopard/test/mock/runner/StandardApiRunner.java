package io.leopard.test.mock.runner;

import java.util.HashSet;
import java.util.Set;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 标准接口Runner(用于验证DAO层代码规范程度)
 * 
 * @author 阿海
 * 
 */
public class StandardApiRunner extends SpringJUnit4ClassRunner {

	public StandardApiRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	private static Set<String> STANDARD_API_NAME = new HashSet<String>();
	static {
		STANDARD_API_NAME.add("get");
		STANDARD_API_NAME.add("add");
		STANDARD_API_NAME.add("list");
		STANDARD_API_NAME.add("map");
		STANDARD_API_NAME.add("delete");
		STANDARD_API_NAME.add("undelete");
		STANDARD_API_NAME.add("incr");
		STANDARD_API_NAME.add("decr");
		STANDARD_API_NAME.add("exist");
		STANDARD_API_NAME.add("updateStatus");
		STANDARD_API_NAME.add("remove");
	}

	private Boolean onlyApi = null;

	protected boolean isOnlyApi() {
		if (onlyApi == null) {
			onlyApi = "true".equals(System.getProperty("api"));
			// System.out.println("api:" + System.getProperty("api"));
		}
		return onlyApi;
	}

	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		if (isOnlyApi()) {
			if (!isStandardApiName(method.getName())) {
				return;
			}
		}
		super.runChild(method, notifier);
	}

	protected boolean isStandardApiName(String methodName) {
		return STANDARD_API_NAME.contains(methodName);
	}

}
