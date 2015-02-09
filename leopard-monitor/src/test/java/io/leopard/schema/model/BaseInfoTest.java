package io.leopard.schema.model;

import io.leopard.monitor.model.BaseInfo;
import io.leopard.test4j.mock.BeanAssert;

import org.junit.Test;

public class BaseInfoTest {

	@Test
	public void BaseInfo() {
		BeanAssert.assertModel(BaseInfo.class);
	}

}