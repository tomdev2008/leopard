package io.leopard.schema.model;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class JdbcUrlInfoTest {

	@Test
	public void JdbcUrlInfo() {
		BeanAssert.assertModel(JdbcUrlInfo.class);
	}

}