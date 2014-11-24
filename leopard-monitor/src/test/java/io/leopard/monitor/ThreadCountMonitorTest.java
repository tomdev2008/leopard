package io.leopard.monitor;

import io.leopard.monitor.alarm.AlarmService;
import io.leopard.schema.model.BaseInfo;
import io.leopard.schema.model.MonitorConfig;
import io.leopard.test.mock.Mock;
import io.leopard.test4j.mock.LeopardMockito;

import org.junit.Test;
import org.mockito.Mockito;

public class ThreadCountMonitorTest {

	ThreadCountMonitor monitor = new ThreadCountMonitor();

	@Test
	public void monitor() {
		AlarmService alarmService = Mockito.mock(AlarmService.class);
		BaseInfo threadCountInfo = Mockito.mock(BaseInfo.class);
		MonitorConfig monitorConfig = Mockito.mock(MonitorConfig.class);
		Mockito.when(monitorConfig.getBaseInfo()).thenReturn(threadCountInfo);
		LeopardMockito.setProperty(monitor, monitorConfig);
		LeopardMockito.setProperty(monitor, alarmService);

		Mockito.doReturn(100000).when(threadCountInfo).getThreadCount();
		monitor.monitor();
		Mock.verify(alarmService, 0).send(Mock.anyString());

		Mockito.doReturn(-1).when(threadCountInfo).getThreadCount();
		monitor.monitor();
		Mock.verify(alarmService, 1).send(Mock.anyString());

	}
}