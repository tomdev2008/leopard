package io.leopard.schema;

import io.leopard.ext.connectionlimit.ConnectionLimitDaoRedisImpl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 接口访问频率限制.
 * 
 * @author 阿海
 * 
 */
public class ConnectionLimitBeanDefinitionParser implements BeanDefinitionParser {

	private final String id = "connectionLimitDao";

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String redisBeanName = element.getAttribute("redis-ref");
		int seconds = Integer.parseInt(element.getAttribute("seconds"));

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ConnectionLimitDaoRedisImpl.class);
		builder.addPropertyReference("redis", redisBeanName);
		builder.addPropertyValue("seconds", seconds);
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);

		return RegisterComponentUtil.registerComponent(parserContext, builder, id);

		// BeanDefinition definition = builder.getBeanDefinition();
		// if (!parserContext.isNested()) {
		// String[] aliases = new String[0];
		// BeanDefinitionHolder holder = new BeanDefinitionHolder(definition,
		// id, aliases);
		// BeanDefinitionReaderUtils.registerBeanDefinition(holder,
		// parserContext.getRegistry());
		// BeanComponentDefinition componentDefinition = new
		// BeanComponentDefinition(holder);
		// parserContext.registerComponent(componentDefinition);
		// }
		// return definition;
	}
}