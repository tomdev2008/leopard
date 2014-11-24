package io.leopard.monitor.thread;

import io.leopard.test.mock.Mock;
import io.leopard.test.mock.MockTests;

import org.junit.Test;
import org.mockito.Mockito;

public class ThreadMonitorServiceImplTest extends MockTests {

	protected ThreadMonitorServiceImpl threadMonitorService = Mock.spy(this, ThreadMonitorServiceImpl.class);
	private ThreadMonitorDao threadMonitorDao;

	@Test
	public void listAll() {
		this.threadMonitorService.listAll();

		Mock.doReturn("[threadName:threadName,category:Dao]").when(threadMonitorDao).listAll();
		this.threadMonitorService.listAll();
	}

	@Test
	public void getActiveCount() {
		ThreadGroup group = Mockito.mock(ThreadGroup.class);
		Mock.doReturn(group).when(this.threadMonitorDao).getThreadGroup();
		this.threadMonitorService.getActiveCount();
	}
}