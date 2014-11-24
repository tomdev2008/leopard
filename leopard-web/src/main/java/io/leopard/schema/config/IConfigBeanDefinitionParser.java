package io.leopard.schema.config;

import org.springframework.beans.factory.xml.ParserContext;

public interface IConfigBeanDefinitionParser {

	void parse(String value, ParserContext parserContext);

}
