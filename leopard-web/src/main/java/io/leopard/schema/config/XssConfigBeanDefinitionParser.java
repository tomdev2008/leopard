package io.leopard.schema.config;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.web.security.xss.XssUtil;

import org.springframework.beans.factory.xml.ParserContext;

/**
 * 是否启用xss.
 * 
 * @author 阿海
 * 
 */
public class XssConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(String value, ParserContext parserContext) {
		boolean isXss = BeanDefinitionParserUtil.isEnable(value);
		XssUtil.setEnable(isXss);
	}

}
