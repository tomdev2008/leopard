package io.leopard.web;

import io.leopard.commons.utility.SystemUtil;
import io.leopard.core.StartService;
import io.leopard.web4j.parameter.PageParameter;
import io.leopard.web4j.resolver.SpeicalParameterHandlerMethodArgumentResolver;
import io.leopard.web4j.validator.ParameterValidator;
import io.leopard.web4j.validator.ParameterValidatorUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class LeopardBeanPostProcessor implements BeanPostProcessor {
	private final Log logger = LogFactory.getLog(LeopardBeanPostProcessor.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof StartService) {
			if (SystemUtil.isNotWindows()) {
				logger.info("StartService 开始执行.");
				((StartService) bean).load();
				logger.info("StartService 执行完成.");
			}
		}

		if (bean instanceof PageParameter) {
			SpeicalParameterHandlerMethodArgumentResolver.registerPageParameter((PageParameter) bean);
		}
		if (bean instanceof ParameterValidator) {
			ParameterValidatorUtil.registerParameterValidator((ParameterValidator) bean);
		}
		return bean;
	}

}
