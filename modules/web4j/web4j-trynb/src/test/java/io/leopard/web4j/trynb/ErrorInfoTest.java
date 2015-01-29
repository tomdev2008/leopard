package io.leopard.web4j.trynb;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class ErrorInfoTest {

	@Test
	public void ErrorInfo() {
		BeanAssert.assertModel(ErrorInfo.class);
	}

}