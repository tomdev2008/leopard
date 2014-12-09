package io.leopard.schema.config;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.web.security.CsrfServiceImpl;

import org.springframework.beans.factory.xml.ParserContext;

public class CsrfConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(String value, ParserContext parserContext) {
		boolean isCsrf = BeanDefinitionParserUtil.isEnable(value);
		CsrfServiceImpl.setEnable(isCsrf);
	}

}
