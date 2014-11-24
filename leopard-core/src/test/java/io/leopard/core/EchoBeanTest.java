package io.leopard.core;

import org.junit.Assert;
import org.junit.Test;

public class EchoBeanTest {

	@Test
	public void EchoBean() {
		EchoBean echoBean = new EchoBean();
		echoBean.setMessage("message");
		Assert.assertEquals("message", echoBean.getMessage());
		echoBean.init();
	}

}