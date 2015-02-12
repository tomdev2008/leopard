package io.leopard.test;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * 智能参数测试方法调用.
 * 
 * @author 阿海
 *
 */
public class SmartParameterInvokeMethod extends Statement {
	private final FrameworkMethod fTestMethod;
	private Object fTarget;

	public SmartParameterInvokeMethod(FrameworkMethod testMethod, Object target) {
		fTestMethod = testMethod;
		fTarget = target;
	}

	@Override
	public void evaluate() throws Throwable {
		for (int i = 0; i < 10; i++) {
			Object[] params = new Object[] { i };
			fTestMethod.invokeExplosively(fTarget, params);
		}
	}

}
