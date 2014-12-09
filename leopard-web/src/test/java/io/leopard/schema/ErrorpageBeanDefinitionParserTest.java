package io.leopard.schema;

import io.leopard.data4j.schema.ElementImpl;
import io.leopard.web4j.trynb.ErrorConfig;
import io.leopard.web4j.trynb.ExceptionConfig;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ErrorpageBeanDefinitionParserTest {

	@Test
	public void ErrorpageBeanDefinitionParser() {

	}

	@Test
	public void parseExceptionConfig() {
		ErrorpageBeanDefinitionParser parser = new ErrorpageBeanDefinitionParser();

		Element element = new ElementImpl();
		element.setAttribute("statusCode", "200");
		ExceptionConfig config = parser.parseExceptionConfig(element);
		Assert.assertEquals(200, config.getStatusCode());
	}

	@Test
	public void parseErrorConfig() {
		ErrorpageBeanDefinitionParser parser = new ErrorpageBeanDefinitionParser();

		Element element = this.getElement();

		ErrorConfig config = parser.parseErrorConfig(element);
		Assert.assertEquals("/index.do", config.getUrl());
		Assert.assertEquals(1, config.getExceptionConfigList().size());
	}

	protected Element getElement() {
		Element element = Mockito.mock(Element.class);
		Mockito.doReturn("/index.do").when(element).getAttribute("url");
		Mockito.doReturn("/error").when(element).getAttribute("page");

		Element node = Mockito.mock(Element.class);
		// node.setAttribute("statusCode", "200");
		Mockito.doReturn("200").when(node).getAttribute("statusCode");

		NodeList nodeList = Mockito.mock(NodeList.class);
		Mockito.doReturn(1).when(nodeList).getLength();
		Mockito.doReturn(node).when(nodeList).item(0);

		Mockito.doReturn(nodeList).when(element).getElementsByTagName("exception");

		return element;

	}

	@Test
	public void parse() {
		ErrorpageBeanDefinitionParser parser = new ErrorpageBeanDefinitionParser();

		Element root = Mockito.mock(Element.class);

		Element node = this.getElement();

		NodeList nodeList = Mockito.mock(NodeList.class);
		Mockito.doReturn(1).when(nodeList).getLength();
		Mockito.doReturn(node).when(nodeList).item(0);

		Mockito.doReturn(nodeList).when(root).getElementsByTagName("error");

		parser.parse(root, null);

	}

}