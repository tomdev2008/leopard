package io.leopard.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class LeopardEnvironment implements BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

		this.call("io.leopard.monitor.MonitorBeanFactoryAware", beanFactory);
	}

	protected void call(String className, BeanFactory beanFactory) {
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			return;
		}
		try {
			BeanFactoryAware beanFactoryAware = (BeanFactoryAware) clazz.newInstance();
			beanFactoryAware.setBeanFactory(beanFactory);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
