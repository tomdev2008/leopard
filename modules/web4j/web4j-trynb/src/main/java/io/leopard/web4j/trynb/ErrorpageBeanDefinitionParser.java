package io.leopard.web4j.trynb;

import io.leopard.web4j.trynb.model.ErrorConfig;
import io.leopard.web4j.trynb.model.ExceptionConfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ErrorpageBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		ErrorPageService errorPageService = new ErrorPageServiceImpl();

		NodeList nodeList = element.getElementsByTagName("error");
		int size = nodeList.getLength();
		for (int i = 0; i < size; i++) {
			Node node = nodeList.item(i);
			ErrorConfig errorConfig = this.parseErrorConfig((Element) node);
//			errorPageService.add(errorConfig);
		}
		// ErrorPageHandlerImpl.setErrorPageService(errorPageService);
		return null;
	}

	protected ErrorConfig parseErrorConfig(Element element) {
		String url = element.getAttribute("url");
		String page = element.getAttribute("page");

		List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
		NodeList nodeList = element.getElementsByTagName("exception");
		int size = nodeList.getLength();
		for (int i = 0; i < size; i++) {
			Node node = nodeList.item(i);
			ExceptionConfig exceptionConfig = this.parseExceptionConfig((Element) node);
			exceptionConfigList.add(exceptionConfig);
		}

		ErrorConfig errorConfig = new ErrorConfig();
		errorConfig.setUrl(url);
		errorConfig.setPage(page);
		errorConfig.setExceptionConfigList(exceptionConfigList);
		return errorConfig;
	}

	protected ExceptionConfig parseExceptionConfig(Element element) {
		String type = element.getAttribute("type");
		String statusCode = element.getAttribute("statusCode").trim();
		String message = element.getAttribute("message");
		String log = element.getAttribute("log");
		ExceptionConfig exceptionConfig = new ExceptionConfig();
		exceptionConfig.setType(type);
		exceptionConfig.setMessage(message);
		exceptionConfig.setStatusCode(statusCode);
		exceptionConfig.setLog(log);
		return exceptionConfig;
	}

	// protected String getNodeValue(NamedNodeMap namedNodeMap, String name) {
	// Node node = namedNodeMap.getNamedItem(name);
	// if (node == null) {
	// return null;
	// }
	// // Attr attr = (Attr) node;
	// // namedNodeMap.
	// // System.out.println("name:" + name + " type:" + attr.getSpecified());
	// return node.getNodeValue();
	// }
}
