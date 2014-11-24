package io.leopard.schema;

import io.leopard.data.env.AppProperties;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.w3c.dom.Element;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ AppProperties.class })
public class EnvBeanDefinitionParserTest {

	protected EnvBeanDefinitionParser befinitionParser = new EnvBeanDefinitionParser();
	private Element element = Mockito.mock(Element.class);

	@Test
	public void parse() throws Exception {
		// PowerMockito.doNothing().when(AppProperties.class);
		// AppProperties.setCurrentConfigFile(ConfigFile.APP_PROPERTIES);
		Mockito.doReturn("app.properties").when(element).getAttribute("configFile");
		Assert.assertNull(befinitionParser.parse(element, null));
	}
}