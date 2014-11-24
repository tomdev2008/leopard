package io.leopard.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class LeopardXmlWebApplicationContext extends XmlWebApplicationContext {

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		return new LeopardDefaultListableBeanFactory(getInternalParentBeanFactory());
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return super.getBean(name);
	}
}
