package io.leopard.monitor.log;

import io.leopard.data4j.env.LogConfigLeiImpl;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

@RunWith(LeopardMockRunner.class)
// @PrepareForTest({ LogConfigLeiImpl.class })
public class Log4jServiceTest {

	@Test
	public void Log4jService() {
		new Log4jService();
	}

	@Test
	public void getLevel() {
		PowerMockito.when(LogConfigLeiImpl.getContent()).thenReturn("log4j.appender.A1.Threshold=INFO");

		Assert.assertEquals("INFO", Log4jService.getLevel("A1"));
	}

	@Test
	public void updateLevel() {
		String str = Log4jService.updateLevel("A1", "WARN", "log4j.appender.A1.Threshold=INFO");
		Assert.assertEquals("log4j.appender.A1.Threshold=WARN", str);
	}

	@Test
	public void update() {
		PowerMockito.when(LogConfigLeiImpl.getContent()).thenReturn("log4j.appender.A1.Threshold=INFO");
		PowerMockito.when(LogConfigLeiImpl.configure(Mockito.anyString())).thenReturn(true);

		String str = Log4jService.update("WARN", "WARN", "WARN", "ERROR");

		Assert.assertEquals("log4j.appender.A1.Threshold=WARN", str);
	}

}