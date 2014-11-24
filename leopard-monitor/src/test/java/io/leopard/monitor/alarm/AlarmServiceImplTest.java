package io.leopard.monitor.alarm;

import io.leopard.test.mock.Mock;
import io.leopard.test.mock.MockTests;

import org.junit.Test;

public class AlarmServiceImplTest extends MockTests {

	protected AlarmServiceImpl alarmService = Mock.spy(this, AlarmServiceImpl.class);

	@Test
	public void send() {
		this.alarmService.send("msg", new Exception());
	}

}