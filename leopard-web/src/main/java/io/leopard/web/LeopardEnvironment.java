package io.leopard.web;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.web.userinfo.ConfigHandler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class LeopardEnvironment implements BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		try {
			beanFactory.getBean(ConfigHandler.class);
		}
		catch (NoSuchBeanDefinitionException e) {
			BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "configHandler", ConfigHandler.class, false);
		}

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
