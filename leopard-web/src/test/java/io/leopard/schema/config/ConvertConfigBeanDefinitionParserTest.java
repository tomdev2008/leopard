package io.leopard.schema.config;

import org.junit.Test;

public class ConvertConfigBeanDefinitionParserTest {

	@Test
	public void parse() {
		new ConvertConfigBeanDefinitionParser().parse("true", null);
	}

}