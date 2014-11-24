package io.leopard.schema;

import org.junit.Test;

public class LeopardNamespaceHandlerTest {

	protected LeopardNamespaceHandler namespaceHandler = new LeopardNamespaceHandler();

	@Test
	public void registerParser() {
		namespaceHandler.registerParser("name", MemcacheDsnBeanDefinitionParser.class.getName() + "No");
		namespaceHandler.registerParser("name", MemcacheDsnBeanDefinitionParser.class.getName());
	}

	@Test
	public void init() {
		namespaceHandler.init();
	}

}