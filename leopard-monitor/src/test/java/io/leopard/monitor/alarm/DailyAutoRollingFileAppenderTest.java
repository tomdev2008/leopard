package io.leopard.monitor.alarm;

import io.leopard.test.mock.Mock;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.junit.Test;
import org.mockito.Mockito;

public class DailyAutoRollingFileAppenderTest {

	@Test
	public void DailyAutoRollingFileAppender() {
		LoggingEvent event = Mockito.mock(LoggingEvent.class);

		DailyAutoRollingFileAppender appender = new DailyAutoRollingFileAppender();

		Mockito.doReturn(Level.INFO).when(event).getLevel();
		appender.append(event);

		Mockito.doReturn(Level.ERROR).when(event).getLevel();
		appender.append(event);

		appender.setName("E1");
		appender.append(event);
	}

	@Test
	public void append() {

	}

	@Test
	public void alarm() {
		DailyAutoRollingFileAppender appender = new DailyAutoRollingFileAppender();
		LoggingEvent event = Mockito.mock(LoggingEvent.class);
		ThrowableInformation throwableInformation = Mockito.mock(ThrowableInformation.class);

		Mock.doReturn(new Exception()).when(throwableInformation).getThrowable();
		Mock.doReturn("message").when(event).getRenderedMessage();
		Mock.doReturn(throwableInformation).when(event).getThrowableInformation();

		appender.alarm(event, "error");

	}

}