package io.leopard.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LeopardClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

	public LeopardClassPathXmlApplicationContext(String... configLocations) throws BeansException {
		super(configLocations);
	}

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		// System.err.println("createBeanFactory:");
		return new LeopardDefaultListableBeanFactory(getInternalParentBeanFactory());
	}
}
