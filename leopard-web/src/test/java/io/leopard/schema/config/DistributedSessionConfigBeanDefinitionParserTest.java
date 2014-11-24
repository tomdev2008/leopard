package io.leopard.schema.config;

import org.junit.Test;

public class DistributedSessionConfigBeanDefinitionParserTest {

	@Test
	public void parse() {
		new SessionRedisConfigBeanDefinitionParser().parse("true", null);
	}

}