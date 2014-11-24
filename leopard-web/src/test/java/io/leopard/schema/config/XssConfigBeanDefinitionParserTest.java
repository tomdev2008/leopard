package io.leopard.schema.config;

import org.junit.Test;

public class XssConfigBeanDefinitionParserTest {

	@Test
	public void parse() {
		new XssConfigBeanDefinitionParser().parse("true", null);
	}

}