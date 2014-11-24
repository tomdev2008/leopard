package io.leopard.schema;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

//@Ignore
@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ParserContext.class, BeanDefinitionParserUtil.class })
public class ConfigBeanDefinitionParserTest {

	// @Test
	// public void toConfigSchema() {
	// Element element = Mockito.mock(Element.class);
	// ConfigBeanDefinitionParser beanDefinitionParser = new ConfigBeanDefinitionParser();
	//
	// {
	// Mockito.doReturn("2").when(element).getAttribute("sessionExpiry");
	// ConfigSchema configSchema = beanDefinitionParser.toConfigSchema(element);
	// Assert.assertEquals(2, configSchema.getSessionExpiry());
	//
	// }
	// {
	// Mockito.doReturn("/udbsdk/").when(element).getAttribute("udbJsFolder");
	// ConfigSchema configSchema = beanDefinitionParser.toConfigSchema(element);
	// Assert.assertEquals("/udbsdk/", configSchema.getUdbJsFolder());
	// }
	// }

	@Test
	public void parse() {
		// AdminFolderConfigBeanDefinitionParser
		ParserContext parserContext = null;

		Node node = Mockito.mock(Node.class);
		Mockito.doReturn("adminFolder").when(node).getNodeName();
		Mockito.doReturn("/udb/").when(node).getNodeValue();

		NamedNodeMap nodeMap = Mockito.mock(NamedNodeMap.class);
		Mockito.doReturn(1).when(nodeMap).getLength();
		// Node node = nodeMap.item(i);
		Mockito.doReturn(node).when(nodeMap).item(0);

		Element element = Mockito.mock(Element.class);

		Mockito.doReturn(nodeMap).when(element).getAttributes();
		// NamedNodeMap nodeMap = element.getAttributes();
		// for (int i = 0; i < nodeMap.getLength(); i++) {
		// Node node = nodeMap.item(i);
		// String name = node.getNodeName();
		// String value = node.getNodeValue();

		ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
		parser.parse(element, parserContext);

	}

	// @Test
	// public void isSpecified() {
	// Element element = Mockito.mock(Element.class);
	// ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
	//
	// ConfigBeanDefinitionParser.configured = false;
	// Assert.assertTrue(parser.isSpecified(element, "name"));
	//
	// ConfigBeanDefinitionParser.configured = true;
	//
	// Attr attr = Mockito.mock(Attr.class);
	// Mockito.doReturn(true).when(attr).getSpecified();
	// // Attr attr = element.getAttributeNode(name);
	// try {
	// parser.isSpecified(element, "name");
	// Assert.fail("为什么没有抛异常?");
	// }
	// catch (NullPointerException e) {
	//
	// }
	// Mockito.doReturn(attr).when(element).getAttributeNode("name");
	// Assert.assertTrue(parser.isSpecified(element, "name"));
	// }

	// @Test
	// public void cleanAttribute() {
	// ConfigBeanDefinitionParser beanDefinitionParser = new ConfigBeanDefinitionParser();
	// beanDefinitionParser.cleanAttribute(element);
	//
	// Mockito.verify(element).removeAttribute("adminType");
	// }

}