package io.leopard.web4j.trynb;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class TrynbNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("trynb", new ErrorpageBeanDefinitionParser());
	}

}