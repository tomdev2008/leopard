package io.leopard.web4j.permission.config;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.permission.config.Allow;

import org.junit.Test;

public class AllowTest {

	@Test
	public void Allow() {
		BeanAssert.assertModel(Allow.class);
	}

}