package io.leopard.schema;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Element;

//@RunWith(LeopardMockRunner.class)
// @PrepareForTest({ ParserContext.class })
public class ComponentScanBeanDefinitionParserTest {

	protected ComponentScanBeanDefinitionParser beanDefinitionParser = Mockito.spy(new ComponentScanBeanDefinitionParser());
	private Element element = Mockito.mock(Element.class);

	@Test
	public void parse() {
		// ParserContext parserContext = PowerMockito.mock(ParserContext.class);
		Mockito.doReturn(null).when((org.springframework.context.annotation.ComponentScanBeanDefinitionParser) this.beanDefinitionParser).parse(element, null);
		Assert.assertNull(beanDefinitionParser.parse(element, null));
	}
}