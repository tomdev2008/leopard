package io.leopard.web4j.trynb;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.trynb.model.ErrorPage;

import org.junit.Test;

public class ErrorPageTest {

	@Test
	public void ErrorPage() {
		BeanAssert.assertModel(ErrorPage.class);
	}

}