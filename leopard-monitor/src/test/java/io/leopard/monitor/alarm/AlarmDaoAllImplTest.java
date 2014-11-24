package io.leopard.monitor.alarm;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;

public class AlarmDaoAllImplTest {

	protected AlarmDaoAllImpl alarmDaoAllImpl = new AlarmDaoAllImpl();

	@Test
	public void send() {
		alarmDaoAllImpl.send("message", new Exception());
	}

	@Test
	public void isNeedSend() {
		try {
			alarmDaoAllImpl.isNeedSend("message");
			Assert.fail("怎么没有抛异常?");
		}
		catch (NotImplementedException e) {

		}
	}
}