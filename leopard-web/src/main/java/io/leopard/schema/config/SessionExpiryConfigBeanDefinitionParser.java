package io.leopard.schema.config;

import io.leopard.web.userinfo.SessionRequestWrapper;

import org.springframework.beans.factory.xml.ParserContext;

/**
 * session超时.
 * 
 * @author 阿海
 * 
 */
public class SessionExpiryConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(String value, ParserContext parserContext) {
		int sessionExpiry = Integer.parseInt(value);
		SessionRequestWrapper.setExpiry(sessionExpiry);

	}

}
