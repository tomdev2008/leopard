package io.leopard.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.leopard.data4j.schema.LeopardAnnotationBeanNameGenerator;
import io.leopard.web.timer.TimerServiceImpl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.w3c.dom.Element;

public class ComponentScanBeanDefinitionParser extends org.springframework.context.annotation.ComponentScanBeanDefinitionParser {

	protected List<String> timerList = new ArrayList<String>();

	// name-generator="io.leopard.core.beans.LeopardAnnotationBeanNameGenerator"
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// BeanDefinitionParserUtil.printParserContext(ComponentScanBeanDefinitionParser.class, parserContext);

		element.setAttribute("name-generator", LeopardAnnotationBeanNameGenerator.class.getName());

		this.createTimerService(parserContext);

		// TODO ahai 排除定时器?
		return super.parse(element, parserContext);
	}

	@Override
	protected void registerComponents(XmlReaderContext readerContext, Set<BeanDefinitionHolder> beanDefinitions, Element element) {
		super.registerComponents(readerContext, beanDefinitions, element);
		for (BeanDefinitionHolder beanDefHolder : beanDefinitions) {
			String beanName = beanDefHolder.getBeanName();
			// TODO ahai 未规范实现
			if (beanName.endsWith("Timer")) {
				// System.err.println("timer source:" + beanName);
				timerList.add(beanName);
			}
		}
	}

	protected BeanDefinition createTimerService(ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(TimerServiceImpl.class);

		ManagedList<RuntimeBeanReference> timerBeanList = new ManagedList<RuntimeBeanReference>();
		for (String beanName : timerList) {
			timerBeanList.add(new RuntimeBeanReference(beanName));
		}
		builder.addPropertyValue("timers", timerBeanList);

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		return RegisterComponentUtil.registerComponent(parserContext, builder, "timerService");
	}

	// protected void registerComponents(XmlReaderContext readerContext,
	// Set<BeanDefinitionHolder> beanDefinitions, Element element) {
	// super.registerComponents(readerContext, beanDefinitions, element);
	// }

}