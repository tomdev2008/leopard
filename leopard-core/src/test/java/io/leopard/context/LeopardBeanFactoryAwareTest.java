package io.leopard.context;

import org.junit.Test;

public class LeopardBeanFactoryAwareTest {

	public static class UserService {

	}

	@Test
	public void setBeanFactory() {
		// BeanFactory beanFactory = Mockito.mock(DefaultListableBeanFactory.class);
		// LeopardBeanFactoryAware aware = new LeopardBeanFactoryAware();
		//
		// aware.setBeanFactory(beanFactory);
		// try {
		// aware.setBeanFactory(beanFactory);
		// Assert.fail("怎么没有抛异常?");
		// }
		// catch (RuntimeException e) {
		//
		// }
		//
		// Assert.assertNotNull(LeopardBeanFactoryAware.getBeanFactory());
		//
		// LeopardBeanFactoryAware.addBean("userService", UserService.class);
		// LeopardBeanFactoryAware.addBean("userService", UserService.class);
	}
}