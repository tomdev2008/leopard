package io.leopard.monitor;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.monitor.forecast.ForecastDaoRedisImpl;
import io.leopard.monitor.forecast.ForecastServiceImpl;
import io.leopard.monitor.forecast.ForecastUtil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class MonitorBeanFactoryAware implements BeanFactoryAware{

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (ForecastUtil.getForecast()) {
			BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "forecastDao", ForecastDaoRedisImpl.class, false);
			BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "forecastService", ForecastServiceImpl.class, false);
		}
	}

}
