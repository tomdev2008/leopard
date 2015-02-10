package io.leopard.web;

import io.leopard.web4j.parameter.PageParameter;
import io.leopard.web4j.resolver.SpeicalParameterHandlerMethodArgumentResolver;
import io.leopard.web4j.validator.ParameterValidator;
import io.leopard.web4j.validator.ParameterValidatorUtil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class LeopardBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof PageParameter) {
			SpeicalParameterHandlerMethodArgumentResolver.registerPageParameter((PageParameter) bean);
		}
		if (bean instanceof ParameterValidator) {
			ParameterValidatorUtil.registerParameterValidator((ParameterValidator) bean);
		}
		return bean;
	}

}
