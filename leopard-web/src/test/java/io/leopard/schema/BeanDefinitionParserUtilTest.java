package io.leopard.schema;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.ParserContext;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ParserContext.class, BeanDefinitionReaderUtils.class })
public class BeanDefinitionParserUtilTest {
	// private Element element = Mockito.mock(Element.class);

	@Test
	public void BeanDefinitionParserUtil() {
		new BeanDefinitionParserUtil();
	}

	@Test
	public void createBean() {
		BeanDefinition beanDefinition = Mockito.mock(BeanDefinition.class);
		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
		ParserContext parserContext = PowerMockito.mock(ParserContext.class);
		Mockito.doReturn(true).when(parserContext).isNested();
		Mockito.doReturn(beanDefinition).when(parserContext).getContainingBeanDefinition();
	}

}