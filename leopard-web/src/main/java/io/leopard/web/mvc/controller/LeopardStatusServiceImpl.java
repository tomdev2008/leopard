package io.leopard.web.mvc.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Service;

@Service("leopardStatusService")
public class LeopardStatusServiceImpl implements LeopardStatusService, BeanFactoryAware {
	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public boolean isEnablePerformanceMonitor() {
		try {
			return beanFactory.getBean("performanceMonitorInterceptor") != null;
		}
		catch (NoSuchBeanDefinitionException e) {
			return false;
		}
	}

}
