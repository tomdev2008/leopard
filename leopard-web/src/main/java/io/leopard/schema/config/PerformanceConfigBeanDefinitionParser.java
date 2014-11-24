package io.leopard.schema.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.ParserContext;

public class PerformanceConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {
	protected Log logger = LogFactory.getLog(this.getClass());

	// private static boolean isCreated = false;

	@Override
	public void parse(String value, ParserContext parserContext) {
		// if (isCreated) {
		// // 防止重复创建.
		// return;
		// }
		// isCreated = true;
		// logger.info("performance enable");
		//
		// BeanDefinitionParserUtil.createBean(parserContext, "topnbController", TopnbController.class, false);
		// BeanDefinitionParserUtil.createBean(parserContext, "performanceMonitorInterceptor", TopnbInterceptor.class, false);
		// BeanDefinitionParserUtil.createBean(parserContext, "beanNameAutoProxyCreatorBean", BeanNameAutoProxyCreatorBean.class, false);
	}

}
