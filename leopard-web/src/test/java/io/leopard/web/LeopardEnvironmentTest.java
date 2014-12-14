package io.leopard.web;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.web.userinfo.ConfigHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ BeanDefinitionParserUtil.class })
public class LeopardEnvironmentTest {
	LeopardEnvironment env = new LeopardEnvironment();

	@Test
	public void setBeanFactory() {
		BeanFactory beanFactory = Mockito.mock(BeanFactory.class);
		env.setBeanFactory(beanFactory);
	}

	@Test
	public void LeopardEnvironment() {
		// AUTO
	}

	@Test
	public void checkConfigHandler() {
		BeanFactory beanFactory = Mockito.mock(BeanFactory.class);
		env.checkConfigHandler(beanFactory);

		// BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "loginHandler", LoginHandler.class, false);
		PowerMockito.when(BeanDefinitionParserUtil.registerBeanDefinition(beanFactory, "configHandler", ConfigHandler.class, false)).thenReturn(true);

		Mockito.doThrow(new NoSuchBeanDefinitionException("configHandler")).when(beanFactory).getBean(ConfigHandler.class);
		env.checkConfigHandler(beanFactory);

	}
}