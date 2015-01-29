package io.leopard.data4j.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LeopardClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

	public LeopardClassPathXmlApplicationContext(String... configLocations) throws BeansException {
		super(configLocations);
		// new Exception("configLocations size:" + configLocations.length).printStackTrace();
	}

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		// System.err.println("createBeanFactory:");
		return new LeopardDefaultListableBeanFactory(getInternalParentBeanFactory());
	}
}
