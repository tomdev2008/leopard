package io.leopard.schema.model;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class ComponentScanSchemaTest {

	@Test
	public void ComponentScanSchema() {
		BeanAssert.assertModel(ComponentScanSchema.class);
	}

}