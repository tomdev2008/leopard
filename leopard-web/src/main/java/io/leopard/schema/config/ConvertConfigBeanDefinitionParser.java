package io.leopard.schema.config;

import io.leopard.schema.BeanDefinitionParserUtil;
import io.leopard.web4j.editor.LeopardWebBindingInitializer;

import org.springframework.beans.factory.xml.ParserContext;

/**
 * 是否启用Leopard参数转换.
 * 
 * @author 阿海
 * 
 */
public class ConvertConfigBeanDefinitionParser implements IConfigBeanDefinitionParser {

	@Override
	public void parse(String value, ParserContext parserContext) {
		boolean isConvert = BeanDefinitionParserUtil.isEnable(value);
		LeopardWebBindingInitializer.setConvert(isConvert);
	}
}
