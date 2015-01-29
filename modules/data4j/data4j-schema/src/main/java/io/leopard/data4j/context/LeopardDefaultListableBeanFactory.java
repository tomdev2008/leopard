package io.leopard.data4j.context;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class LeopardDefaultListableBeanFactory extends DefaultListableBeanFactory {

	protected Map<String, String> map = new HashMap<String, String>();

	public LeopardDefaultListableBeanFactory(BeanFactory parentBeanFactory) {
		super(parentBeanFactory);

		try {
			this.loadAutoBeanInfo();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	protected void loadAutoBeanInfo() throws IOException {
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:/autobean.properties");
		for (Resource resource : resources) {
			// System.err.println("resource:" + resource.getURI().toString());
			Properties config = PropertiesLoaderUtils.loadProperties(resource);
			for (Entry<Object, Object> entry : config.entrySet()) {
				map.put((String) entry.getKey(), (String) entry.getValue());
			}

		}
	}

	@Override
	protected Map<String, Object> findAutowireCandidates(String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {
		// if (requiredType.equals(NewsDao.class)) {
		// System.err.println("NewsDao:" + requiredType.getName());
		// }
		// System.err.println("findAutowireCandidates:" + beanName + " " + requiredType.getName());

		Map<String, Object> map = super.findAutowireCandidates(beanName, requiredType, descriptor);
		if (map == null || map.isEmpty()) {
			map = this.findAutoBean(beanName, requiredType, descriptor);
		}
		return map;
	}

	protected Map<String, Object> findAutoBean(String beanName, Class<?> requiredType, DependencyDescriptor descriptor) {
		String requiredTypeName = requiredType.getName();
		String requiredTypeImplName = map.get(requiredTypeName);
		if (requiredTypeImplName == null) {
			return new HashMap<String, Object>();
		}

		Class<?> clazz;
		try {
			clazz = Class.forName(requiredTypeImplName);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		BeanDefinitionParserUtil.registerBeanDefinition(this, requiredTypeImplName, clazz, false);
		// System.err.println("Leopard自动注册Spring Bean:" + requiredTypeName + "=" + requiredTypeImplName);
		return super.findAutowireCandidates(beanName, requiredType, descriptor);
	}
}
