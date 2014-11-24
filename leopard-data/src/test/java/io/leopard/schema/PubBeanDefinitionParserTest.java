package io.leopard.schema;

import io.leopard.data.pub.PubSubRsyncImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class PubBeanDefinitionParserTest {

	@Test
	public void getBeanClass() {
		PubBeanDefinitionParser parser = new PubBeanDefinitionParser();
		Assert.assertEquals(PubSubRsyncImpl.class, parser.getBeanClass(null));
	}

	@Test
	public void doParse() {
		ElementImpl element = new ElementImpl();

		BeanDefinitionBuilder builder = Mockito.mock(BeanDefinitionBuilder.class);
		PubBeanDefinitionParser parser = new PubBeanDefinitionParser();
		parser.doParse(element, builder);
	}

}