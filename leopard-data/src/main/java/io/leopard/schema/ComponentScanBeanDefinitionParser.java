package io.leopard.schema;

import io.leopard.data4j.schema.LeopardAnnotationBeanNameGenerator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ComponentScanBeanDefinitionParser extends org.springframework.context.annotation.ComponentScanBeanDefinitionParser {

	// name-generator="io.leopard.core.beans.LeopardAnnotationBeanNameGenerator"
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// BeanDefinitionParserUtil.printParserContext(ComponentScanBeanDefinitionParser.class, parserContext);

		element.setAttribute("name-generator", LeopardAnnotationBeanNameGenerator.class.getName());
		// TODO ahai 排除定时器?
		return super.parse(element, parserContext);
	}

	// protected void registerComponents(XmlReaderContext readerContext,
	// Set<BeanDefinitionHolder> beanDefinitions, Element element) {
	// super.registerComponents(readerContext, beanDefinitions, element);
	// }

}