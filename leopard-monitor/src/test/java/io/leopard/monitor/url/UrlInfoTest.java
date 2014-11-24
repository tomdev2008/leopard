package io.leopard.monitor.url;

import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class UrlInfoTest {

	@Test
	public void UrlInfo() {
		BeanAssert.assertModel(UrlInfo.class);
	}

}