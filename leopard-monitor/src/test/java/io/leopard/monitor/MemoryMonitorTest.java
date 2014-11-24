package io.leopard.monitor;

import io.leopard.monitor.alarm.AlarmService;
import io.leopard.schema.model.BaseInfo;
import io.leopard.schema.model.MonitorConfig;
import io.leopard.test.mock.Mock;
import io.leopard.test4j.mock.LeopardMockito;

import org.junit.Test;
import org.mockito.Mockito;

public class MemoryMonitorTest {

	MemoryMonitor monitor = new MemoryMonitor();

	@Test
	public void freePhysicalMemory() {
		AlarmService alarmService = Mockito.mock(AlarmService.class);

		BaseInfo threadCountInfo = Mockito.mock(BaseInfo.class);

		MonitorConfig monitorConfig = Mockito.mock(MonitorConfig.class);
		Mockito.when(monitorConfig.getBaseInfo()).thenReturn(threadCountInfo);

		LeopardMockito.setProperty(monitor, monitorConfig);
		LeopardMockito.setProperty(monitor, alarmService);

		Mockito.doReturn(10L * 1024 * 1024 * 1024 * 1024).when(threadCountInfo).parseFreePhysicalMemory();
		monitor.freePhysicalMemory();
		Mock.verify(alarmService, 0).send(Mock.anyString());

		Mockito.doReturn(-2L).when(threadCountInfo).parseFreePhysicalMemory();
		monitor.freePhysicalMemory();
		Mock.verify(alarmService, 0).send(Mock.anyString());
	}

	@Test
	public void usedHeapMemory() {
		AlarmService alarmService = Mockito.mock(AlarmService.class);

		BaseInfo threadCountInfo = Mockito.mock(BaseInfo.class);

		MonitorConfig monitorConfig = Mockito.mock(MonitorConfig.class);
		Mockito.when(monitorConfig.getBaseInfo()).thenReturn(threadCountInfo);

		LeopardMockito.setProperty(monitor, monitorConfig);
		LeopardMockito.setProperty(monitor, alarmService);

		Mockito.doReturn(10L * 1024 * 1024 * 1024 * 1024).when(threadCountInfo).parseUsedHeapMemory();
		monitor.usedHeapMemory();
		Mock.verify(alarmService, 0).send(Mock.anyString());

		Mockito.doReturn(-2L).when(threadCountInfo).parseUsedHeapMemory();
		monitor.usedHeapMemory();
		Mock.verify(alarmService, 1).send(Mock.anyString());
	}

	@Test
	public void monitor() {
		AlarmService alarmService = Mockito.mock(AlarmService.class);

		BaseInfo threadCountInfo = Mockito.mock(BaseInfo.class);

		MonitorConfig monitorConfig = Mockito.mock(MonitorConfig.class);
		Mockito.when(monitorConfig.getBaseInfo()).thenReturn(threadCountInfo);

		LeopardMockito.setProperty(monitor, monitorConfig);
		LeopardMockito.setProperty(monitor, alarmService);
		this.monitor.monitor();
	}
}