package io.leopard.schema.config;

import org.junit.Test;

public class CsrfConfigBeanDefinitionParserTest {

	@Test
	public void parse() {
		new CsrfConfigBeanDefinitionParser().parse("true", null);
	}

}