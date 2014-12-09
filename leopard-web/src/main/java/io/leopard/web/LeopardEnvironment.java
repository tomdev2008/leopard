package io.leopard.web;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.monitor.forecast.ForecastDaoRedisImpl;
import io.leopard.monitor.forecast.ForecastServiceImpl;
import io.leopard.monitor.forecast.ForecastUtil;
import io.leopard.web.userinfo.service.ConfigHandler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public class LeopardEnvironment implements BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.checkConfigHandler(beanFactory);
		// this.checkLoginBox(beanFactory);
		this.registerForecastBean(beanFactory);
	}

	protected void checkConfigHandler(BeanFactory beanFactory) {
		try {
			beanFactory.getBean(ConfigHandler.class);
		} catch (NoSuchBeanDefinitionException e) {
			BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "configHandler", ConfigHandler.class, false);
		}
	}

	// protected void checkLoginBox(BeanFactory beanFactory) {
	// try {
	// beanFactory.getBean(LoginBox.class);
	// } catch (NoSuchBeanDefinitionException e) {
	// BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "loginBox", LoginBoxImpl.class, false);
	// }
	// }

	protected void registerForecastBean(BeanFactory beanFactory) {
		if (ForecastUtil.getForecast()) {
			BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "forecastDao", ForecastDaoRedisImpl.class, false);
			BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "forecastService", ForecastServiceImpl.class, false);
		}
	}
}
