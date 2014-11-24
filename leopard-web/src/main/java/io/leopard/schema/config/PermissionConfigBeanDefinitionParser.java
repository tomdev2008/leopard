package io.leopard.schema.config;

import io.leopard.schema.BeanDefinitionParserUtil;
import io.leopard.web4j.permission.PermissionDaoCacheImpl;
import io.leopard.web4j.permission.PermissionDaoMemoryImpl;
import io.leopard.web4j.permission.PermissionDaoMysqlImpl;
import io.leopard.web4j.permission.PermissionServiceImpl;

import org.springframework.beans.factory.xml.ParserContext;

public class PermissionConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(String value, ParserContext parserContext) {

		BeanDefinitionParserUtil.createBean(parserContext, "permissionDaoMemoryImpl", PermissionDaoMemoryImpl.class, false);
		BeanDefinitionParserUtil.createBean(parserContext, "permissionDaoMysqlImpl", PermissionDaoMysqlImpl.class, false);
		BeanDefinitionParserUtil.createBean(parserContext, "permissionDaoCacheImpl", PermissionDaoCacheImpl.class, false);

		BeanDefinitionParserUtil.createBean(parserContext, "permissionService", PermissionServiceImpl.class, false);

	}

}
