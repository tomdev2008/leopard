package io.leopard.schema;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.xml.ParserContext;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ParserContext.class })
public class BeanDefinitionParserUtilTest {

	public static class UserService {

	}

	@Test
	public void createBean() {
		// ParserContext parserContext = PowerMockito.mock(ParserContext.class);
		// BeanDefinitionParserUtil.createBean(parserContext, UserService.class, "userService");
	}

	@Test
	public void isEnable() {
		// ConfigBeanLazyRegister configBeanLazyRegister = new ConfigBeanLazyRegister();

		Assert.assertTrue(BeanDefinitionParserUtil.isEnable("true"));
		Assert.assertTrue(BeanDefinitionParserUtil.isEnable("enable"));
		Assert.assertFalse(BeanDefinitionParserUtil.isEnable("false"));
		Assert.assertFalse(BeanDefinitionParserUtil.isEnable("disable"));

	}

}