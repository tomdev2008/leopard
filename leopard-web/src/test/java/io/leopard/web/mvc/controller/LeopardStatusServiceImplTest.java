package io.leopard.web.mvc.controller;

import io.leopard.test.mock.MockTests;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;

public class LeopardStatusServiceImplTest extends MockTests {

	protected LeopardStatusServiceImpl leopardStatusService = new LeopardStatusServiceImpl();

	@Test
	public void isEnablePerformanceMonitor() {
		BeanFactory beanFactory = Mockito.mock(BeanFactory.class);
		leopardStatusService.setBeanFactory(beanFactory);

		Mockito.doReturn(null).when(beanFactory).getBean("performanceMonitorInterceptor");
		Assert.assertFalse(leopardStatusService.isEnablePerformanceMonitor());

		Mockito.doReturn("").when(beanFactory).getBean("performanceMonitorInterceptor");
		Assert.assertTrue(leopardStatusService.isEnablePerformanceMonitor());
	}

}