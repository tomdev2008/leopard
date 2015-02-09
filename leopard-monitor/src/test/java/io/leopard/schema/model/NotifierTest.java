package io.leopard.schema.model;

import io.leopard.monitor.model.Notifier;
import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class NotifierTest {

	@Test
	public void Notifier() {
		BeanAssert.assertModel(Notifier.class);
	}

}