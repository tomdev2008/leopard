package io.leopard.schema;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ParserContext.class })
public class ConnectionLimitBeanDefinitionParserTest {

	private final Element element = Mockito.mock(Element.class);

	@Test
	public void parse() {
		ConnectionLimitBeanDefinitionParser beanDefinitionParser = Mockito.spy(new ConnectionLimitBeanDefinitionParser());
		ParserContext parserContext = PowerMockito.mock(ParserContext.class);
		Mockito.doReturn("redis").when(this.element).getAttribute("redis-ref");
		Mockito.doReturn("3").when(this.element).getAttribute("seconds");
		Mockito.doReturn(true).when(parserContext).isNested();
		BeanDefinition beanDefinition = beanDefinitionParser.parse(element, parserContext);

		RuntimeBeanReference redisReference = (RuntimeBeanReference) beanDefinition.getPropertyValues().getPropertyValue("redis").getValue();
		Assert.assertEquals("redis", redisReference.getBeanName());

	}
}