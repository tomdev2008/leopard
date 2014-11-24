package io.leopard.context;

import org.junit.Assert;
import org.junit.Test;

public class LeopardClassPathXmlApplicationContextTest {

	@Test
	public void LeopardClassPathXmlApplicationContext() {
		LeopardClassPathXmlApplicationContext applicationContext = new LeopardClassPathXmlApplicationContext("applicationContext.xml");
		Assert.assertNotNull(applicationContext.createBeanFactory());
		applicationContext.close();
	}

}