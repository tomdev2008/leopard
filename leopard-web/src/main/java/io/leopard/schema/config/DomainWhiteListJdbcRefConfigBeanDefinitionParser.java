package io.leopard.schema.config;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.data4j.schema.BeanDefinitionParserUtil.DoParser;
import io.leopard.web4j.nobug.csrf.DomainWhiteListConfigImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;

/**
 * 注册域名白名单bean.
 * 
 * @author 阿海
 * 
 */
public class DomainWhiteListJdbcRefConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(final String jdbcName, ParserContext parserContext) {
		if (StringUtils.isEmpty(jdbcName)) {
			return;
		}

		BeanDefinitionParserUtil.createBean(parserContext, "domainWhiteListConfig", DomainWhiteListConfigImpl.class, true, new DoParser() {
			@Override
			public void doParse(BeanDefinitionBuilder builder) {
				builder.addPropertyReference("jdbc", jdbcName);
			}
		});
	}

}
