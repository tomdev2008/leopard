package io.leopard.schema.model;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class ConfigSchemaTest {

	@Test
	public void ConfigSchema() {
		BeanAssert.assertModel(ConfigSchema.class);
	}

}