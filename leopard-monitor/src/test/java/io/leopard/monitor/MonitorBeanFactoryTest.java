package io.leopard.monitor;

import org.junit.Assert;
import org.junit.Test;

public class MonitorBeanFactoryTest {

	@Test
	public void PerformanceBeanFactory() {
		new MonitorBeanFactory();
	}

	@Test
	public void getThreadMonitorService() {
		Assert.assertNotNull(MonitorBeanFactory.getThreadMonitorService());
	}

	// @Test
	// public void getPerformanceHandler() {
	// Assert.assertNotNull(MonitorBeanFactory.getPerformanceHandler());
	// }

	// @Test
	// public void getPerformanceQueueService() {
	// Assert.assertNotNull(MonitorBeanFactory.getPerformanceQueueService());
	// }

}