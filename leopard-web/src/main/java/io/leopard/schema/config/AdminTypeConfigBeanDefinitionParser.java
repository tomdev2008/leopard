package io.leopard.schema.config;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.web4j.admin.dao.AdminDaoMysqlImpl;

import org.springframework.beans.factory.xml.ParserContext;

public class AdminTypeConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(final String adminType, ParserContext parserContext) {
		BeanDefinitionParserUtil.createBean(parserContext, "adminDaoMysqlImpl", AdminDaoMysqlImpl.class, true);
	}

}
