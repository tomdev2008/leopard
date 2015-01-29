package io.leopard.schema;

import io.leopard.data4j.env.LeopardPropertyPlaceholderConfigurer;
import io.leopard.data4j.env.PropertyPlaceholderLei;
import io.leopard.data4j.env.PropertyPlaceholderLeiImpl;
import io.leopard.data4j.env.ResolvePlaceholderLei;
import io.leopard.data4j.env.ResolvePlaceholderLeiImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class PropertyPlaceholderBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	protected Class<?> getBeanClass(Element element) {
		return LeopardPropertyPlaceholderConfigurer.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		// <leopard:signature id="newsSignatureService" redis-ref="signatureRedisLog4jImpl" redisKey="news_signature" publicKey="sha1.newsxxxxxxx232232"/>
		element.setAttribute("id", "LeopardPropertyPlaceholder");
		String className = element.getAttribute("clazz");
		PropertyPlaceholderLei propertyPlaceholderLei;
		if (StringUtils.isEmpty(className)) {
			propertyPlaceholderLei = new PropertyPlaceholderLeiImpl();
		}
		else {
			try {
				propertyPlaceholderLei = (PropertyPlaceholderLei) Class.forName(className).newInstance();
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		builder.addPropertyValue("propertyPlaceholderLei", propertyPlaceholderLei);

		ResolvePlaceholderLei resolvePlaceholderLei = new ResolvePlaceholderLeiImpl();
		builder.addPropertyValue("resolvePlaceholderLei", resolvePlaceholderLei);

	}

}
