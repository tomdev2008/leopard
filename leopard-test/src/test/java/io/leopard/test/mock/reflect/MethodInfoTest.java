package io.leopard.test.mock.reflect;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class MethodInfoTest {

	@Test
	public void MethodInfo() {
		BeanAssert.assertModel(MethodInfo.class);
	}

}