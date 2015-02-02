package io.leopard.web.mvc;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

public class ResourcesHandlerMapping extends SimpleUrlHandlerMapping {

	public ResourcesHandlerMapping() {
		this.setOrder(Ordered.LOWEST_PRECEDENCE - 2);// 比<mvc:resources/>小1.
		// this.setUrlMap(urlMap);
	}

	void test() {
		// RootBeanDefinition handlerMappingDef = new RootBeanDefinition(SimpleUrlHandlerMapping.class);
		// handlerMappingDef.setSource(source);
		// handlerMappingDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		// handlerMappingDef.getPropertyValues().add("urlMap", urlMap);
		//
		// String order = element.getAttribute("order");
		// // use a default of near-lowest precedence, still allowing for even lower precedence in other mappings
		// handlerMappingDef.getPropertyValues().add("order", StringUtils.hasText(order) ? order : Ordered.LOWEST_PRECEDENCE - 1);
		//
		// String beanName = parserContext.getReaderContext().generateBeanName(handlerMappingDef);
		// parserContext.getRegistry().registerBeanDefinition(beanName, handlerMappingDef);
		// parserContext.registerComponent(new BeanComponentDefinition(handlerMappingDef, beanName));
	}
}
