package io.leopard.schema.config;

import org.junit.Test;

public class SessionExpiryConfigBeanDefinitionParserTest {

	@Test
	public void parse() {
		new SessionExpiryConfigBeanDefinitionParser().parse("1000", null);
	}

}