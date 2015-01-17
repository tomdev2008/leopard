package io.leopard.test;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class LeopardJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	public LeopardJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

		// NoFastBean noFastBean = clazz.getAnnotation(NoFastBean.class);
		// System.out.println("clazz:" + clazz.getName() + " noFastBean:" + noFastBean);
		// if (noFastBean != null) {
		// TestContextLoader.setFastBean(false);
		// }

		// TestContextLoader
	}

}
