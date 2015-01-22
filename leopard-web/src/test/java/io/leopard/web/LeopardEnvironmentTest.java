package io.leopard.web;

import io.leopard.data4j.schema.BeanDefinitionParserUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.BeanFactory;

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

}