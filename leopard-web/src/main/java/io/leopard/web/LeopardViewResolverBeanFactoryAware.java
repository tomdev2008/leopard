package io.leopard.web;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

public class LeopardViewResolverBeanFactoryAware implements BeanFactoryAware {

	@Value("${leopard.view.type}")
	private String type;

	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

		System.err.println("view type:" + type);

		if ("${leopard.view.type}".equals(type)) {
			type = null;
		}
		type = "jsp";
		if ("jsp".equalsIgnoreCase(type)) {
			registerInternalResourceViewResolverBeanDefinition();
		}
		else {
			registercreateFreeMarkerViewResolverBeanDefinition();
		}
	}

	protected void registerInternalResourceViewResolverBeanDefinition() {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(InternalResourceViewResolver.class);
		beanDefinitionBuilder.addPropertyValue("viewClass", "org.springframework.web.servlet.view.JstlView");
		beanDefinitionBuilder.addPropertyValue("prefix", "/WEB-INF/jsp/");
		beanDefinitionBuilder.addPropertyValue("suffix", ".jsp");
		beanDefinitionBuilder.addPropertyValue("contentType", "text/html; charset=UTF-8");
		BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "viewResolver", beanDefinitionBuilder);
	}

	// <bean id="viewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
	// <property name="order" value="1" />
	// <property name="cache" value="false" />
	// <property name="suffix" value=".ftl" />
	// <property name="exposeSpringMacroHelpers" value="true" />
	// <property name="contentType" value="text/html;charset=UTF-8"></property>
	// </bean>
	protected void registercreateFreeMarkerViewResolverBeanDefinition() {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(InternalResourceViewResolver.class);
		beanDefinitionBuilder.addPropertyValue("viewClass", "org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver");
		beanDefinitionBuilder.addPropertyValue("order", "1");
		beanDefinitionBuilder.addPropertyValue("cache", "false");
		beanDefinitionBuilder.addPropertyValue("exposeSpringMacroHelpers", "true");
		beanDefinitionBuilder.addPropertyValue("prefix", "/WEB-INF/freemarker/");
		beanDefinitionBuilder.addPropertyValue("suffix", "ftl");
		beanDefinitionBuilder.addPropertyValue("contentType", "text/html; charset=UTF-8");
		BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "viewResolver", beanDefinitionBuilder);
	}

}
