package io.leopard.schema.config;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.data4j.schema.BeanDefinitionParserUtil.DoParser;
import io.leopard.web4j.session.SessionServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;

/**
 * 是否启用分布式session.
 * 
 * @author 阿海
 * 
 */
public class SessionRedisConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(final String redisRef, ParserContext parserContext) {
		// boolean isEnableDistributedSession =
		// StringUtils.isNotEmpty(redisRef);

		BeanDefinitionParserUtil.createBean(parserContext, "sessionService", SessionServiceImpl.class, false, new DoParser() {
			@Override
			public void doParse(BeanDefinitionBuilder builder) {
				if (StringUtils.isNotEmpty(redisRef)) {
					builder.addPropertyReference("redis", redisRef);
				}
			}
		});

		// SessionServiceImpl.setEnableDistributedSession(isEnableDistributedSession);
	}

}
