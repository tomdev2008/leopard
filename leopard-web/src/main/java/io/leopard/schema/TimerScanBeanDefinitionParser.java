package io.leopard.schema;

import io.leopard.data4j.schema.LeopardAnnotationBeanNameGenerator;
import io.leopard.web.timer.TimerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.w3c.dom.Element;

public class TimerScanBeanDefinitionParser extends org.springframework.context.annotation.ComponentScanBeanDefinitionParser {
	protected List<String> timerList = new ArrayList<String>();

	private String id = "timerService";

	// name-generator="io.leopard.core.beans.LeopardAnnotationBeanNameGenerator"
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// BeanDefinitionParserUtil.printParserContext(TimerScanBeanDefinitionParser.class, parserContext);

		// System.err.println("parse base-package:" + element.getAttribute("base-package"));
		element.setAttribute("name-generator", LeopardAnnotationBeanNameGenerator.class.getName());
		super.parse(element, parserContext);

		// TODO ahai 增加是否webserver的判断.
		this.createTimerService(parserContext);
		return null;
	}

	protected void registerComponents(XmlReaderContext readerContext, Set<BeanDefinitionHolder> beanDefinitions, Element element) {
		super.registerComponents(readerContext, beanDefinitions, element);
		for (BeanDefinitionHolder beanDefHolder : beanDefinitions) {
			String beanName = beanDefHolder.getBeanName();
			// System.err.println("timer beanName:" + beanName);
			timerList.add(beanName);
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
		return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	}
}