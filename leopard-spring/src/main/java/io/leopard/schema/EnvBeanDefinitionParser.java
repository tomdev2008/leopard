package io.leopard.schema;

import io.leopard.data.env.AppProperties;
import io.leopard.data.env.AppProperties.ConfigFile;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * app.properties配置
 * 
 * @author 阿海
 * 
 */
public class EnvBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String name = element.getAttribute("configFile");

		AppProperties.setCurrentConfigFile(ConfigFile.toEnum(name));
		return null;
	}

}