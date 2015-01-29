package io.leopard.web4j.trynb;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class WebserviceStatusTest {

	@Test
	public void WebserviceStatus() {
		BeanAssert.assertModel(WebserviceStatus.class);
	}

}