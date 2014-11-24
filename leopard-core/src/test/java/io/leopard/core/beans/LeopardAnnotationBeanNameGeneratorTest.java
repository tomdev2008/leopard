package io.leopard.core.beans;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class LeopardAnnotationBeanNameGeneratorTest {

	protected LeopardAnnotationBeanNameGenerator leopardAnnotationBeanNameGenerator = new LeopardAnnotationBeanNameGenerator();

	@Test
	public void replaceBeanName() {
		Assert.assertEquals("userDaoCacheImpl", leopardAnnotationBeanNameGenerator.replaceBeanName("userDaoCacheImpl"));
		Assert.assertEquals("userDaoMysqlImpl", leopardAnnotationBeanNameGenerator.replaceBeanName("userDaoMysqlImpl"));
		Assert.assertEquals("userService", leopardAnnotationBeanNameGenerator.replaceBeanName("userServiceImpl"));
		Assert.assertEquals("userHandler", leopardAnnotationBeanNameGenerator.replaceBeanName("userHandlerImpl"));
		Assert.assertEquals("userController", leopardAnnotationBeanNameGenerator.replaceBeanName("userController"));
		Assert.assertEquals("userControllerImpl", leopardAnnotationBeanNameGenerator.replaceBeanName("userControllerImpl"));
	}

	@Test
	public void initPrimaryBean() {
		BeanDefinition definition = new GenericBeanDefinition();
		definition.setBeanClassName("userDaoCacheImpl");
		Assert.assertFalse(definition.isPrimary());
		leopardAnnotationBeanNameGenerator.initPrimaryBean(definition);
		Assert.assertTrue(definition.isPrimary());
	}

	@Test
	public void buildDefaultBeanName() {
		BeanDefinition definition = new GenericBeanDefinition();
		definition.setBeanClassName("userDaoCacheImpl");
		Assert.assertFalse(definition.isPrimary());
		leopardAnnotationBeanNameGenerator.buildDefaultBeanName(definition);
		Assert.assertTrue(definition.isPrimary());
	}

	@Test
	public void buildDefaultBeanName2() {
		BeanDefinition definition = new GenericBeanDefinition();
		definition.setBeanClassName("userServiceImpl");
		String beanName = leopardAnnotationBeanNameGenerator.buildDefaultBeanName(definition);
		Assert.assertEquals("userService", beanName);
	}
}