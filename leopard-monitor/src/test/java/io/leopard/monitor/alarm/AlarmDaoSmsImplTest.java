package io.leopard.monitor.alarm;

import io.leopard.burrow.httpnb.Httpnb;
import io.leopard.monitor.MonitorServiceImpl;
import io.leopard.schema.model.MonitorConfig;
import io.leopard.schema.model.Notifier;
import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.test4j.mock.LeopardMockito;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ MonitorServiceImpl.class })
public class AlarmDaoSmsImplTest {

	protected AlarmDaoSmsImpl alarmDaoSmsImpl = new AlarmDaoSmsImpl();

	@SuppressWarnings("unchecked")
	@Test
	public void send() {
		Assert.assertTrue(this.alarmDaoSmsImpl.send("message", new Exception()));
		Assert.assertFalse(this.alarmDaoSmsImpl.send("", new Exception("message")));
		// AlarmDaoSmsImpl.setSmsEnable(false);
		// Assert.assertTrue(this.alarmDaoSmsImpl.send("message", new Exception()));
		AlarmDaoSmsImpl.setSmsEnable(true);

		MonitorConfig monitorConfig = Mockito.mock(MonitorConfig.class);

		{

			// HttpUtils.doPost(url, params, 2000, 2000);

			PowerMockito.when(Httpnb.doPost(Mockito.anyString(), Mockito.anyLong(), Mockito.anyMap())).thenReturn(null);
			List<Notifier> notifierList = new ArrayList<Notifier>();
			Notifier notifier = new Notifier();
			notifier.setMobile("12345678");
			notifier.setName("ahai");
			notifierList.add(notifier);
			Mockito.when(monitorConfig.getNotifierList()).thenReturn(notifierList);
			PowerMockito.when(MonitorServiceImpl.getMonitorConfig()).thenReturn(monitorConfig);
			Assert.assertTrue(this.alarmDaoSmsImpl.send("message2", new Exception()));
		}

		// /
		AlarmFrequencyDao alarmFrequencyDaoMemoryImpl = Mockito.mock(AlarmFrequencyDao.class);
		LeopardMockito.setProperty(alarmDaoSmsImpl, alarmFrequencyDaoMemoryImpl);
		Assert.assertFalse(this.alarmDaoSmsImpl.send("message", new Exception()));
	}

	@Test
	public void isNeedSend() {
		AlarmDaoSmsImpl alarmDaoSmsImpl = new AlarmDaoSmsImpl();
		Assert.assertTrue(alarmDaoSmsImpl.isNeedSend("message"));
	}

	@Test
	public void setSmsEnable() {
		AlarmDaoSmsImpl.setSmsEnable(true);
	}

}