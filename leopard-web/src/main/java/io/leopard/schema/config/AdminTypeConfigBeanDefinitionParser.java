package io.leopard.schema.config;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.data4j.schema.BeanDefinitionParserUtil.DoParser;
import io.leopard.web4j.admin.dao.AdminLoginDaoMysqlImpl;
import io.leopard.web4j.admin.dao.AdminLoginServiceImpl;
import io.leopard.web4j.admin.dao.SystemAdminServiceImpl;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;

public class AdminTypeConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(final String adminType, ParserContext parserContext) {
		BeanDefinitionParserUtil.createBean(parserContext, "adminLoginDaoMysqlImpl", AdminLoginDaoMysqlImpl.class, true);

		{

			BeanDefinitionParserUtil.createBean(parserContext, "adminLoginService", AdminLoginServiceImpl.class, true, new DoParser() {
				@Override
				public void doParse(BeanDefinitionBuilder builder) {
//					builder.addPropertyReference("adminLoginDao", "adminLoginDaoMysqlImpl");
				}
			});
		}

		BeanDefinitionParserUtil.createBean(parserContext, "systemAdminService", SystemAdminServiceImpl.class, false);
	}

}
