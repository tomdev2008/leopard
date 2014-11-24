package io.leopard.monitor.alarm;

import org.junit.Assert;
import org.junit.Test;

public class AlarmFrequencyDaoMemoryImplTest {

	@Test
	public void isSent() {
		String message = "message";
		int intervalSeconds = 10;

		AlarmFrequencyDaoMemoryImpl alarmFrequencyDaoMemoryImpl = new AlarmFrequencyDaoMemoryImpl();
		Assert.assertFalse(alarmFrequencyDaoMemoryImpl.isSent(message, intervalSeconds));
		alarmFrequencyDaoMemoryImpl.lastSendTimeMap.put(message, System.currentTimeMillis() - 11 * 1000);
		Assert.assertFalse(alarmFrequencyDaoMemoryImpl.isSent(message, intervalSeconds));

		alarmFrequencyDaoMemoryImpl.lastSendTimeMap.put(message, System.currentTimeMillis() - 1 * 1000);
		Assert.assertTrue(alarmFrequencyDaoMemoryImpl.isSent(message, intervalSeconds));
	}

	@Test
	public void isUpperLimit() {
		AlarmFrequencyDaoMemoryImpl alarmFrequencyDaoMemoryImpl = new AlarmFrequencyDaoMemoryImpl();
		Assert.assertFalse(alarmFrequencyDaoMemoryImpl.isUpperLimit());
		for (int i = 0; i < 9; i++) {
			alarmFrequencyDaoMemoryImpl.upperLimitMap.put(System.currentTimeMillis() - (1 + i) * 1000, "");
		}
		Assert.assertFalse(alarmFrequencyDaoMemoryImpl.isUpperLimit());

		alarmFrequencyDaoMemoryImpl.upperLimitMap.put(System.currentTimeMillis() - 5 * 60 * 60 * 1000, "");
		Assert.assertFalse(alarmFrequencyDaoMemoryImpl.isUpperLimit());

		alarmFrequencyDaoMemoryImpl.upperLimitMap.put(System.currentTimeMillis() - 20 * 1000, "");
		Assert.assertTrue(alarmFrequencyDaoMemoryImpl.isUpperLimit());
	}

	@Test
	public void add() {
		AlarmFrequencyDaoMemoryImpl alarmFrequencyDaoMemoryImpl = new AlarmFrequencyDaoMemoryImpl();
		alarmFrequencyDaoMemoryImpl.add("message");

		Assert.assertEquals(1, alarmFrequencyDaoMemoryImpl.lastSendTimeMap.size());
	}

	@Test
	public void isNeedSend() {
		String message = "message";
		int intervalSeconds = 10;
		AlarmFrequencyDaoMemoryImpl alarmFrequencyDaoMemoryImpl = new AlarmFrequencyDaoMemoryImpl();
		Assert.assertTrue(alarmFrequencyDaoMemoryImpl.isNeedSend(message, intervalSeconds));

		for (int i = 0; i < 9; i++) {
			alarmFrequencyDaoMemoryImpl.upperLimitMap.put(System.currentTimeMillis() - (1 + i) * 1000, "");
		}
		Assert.assertTrue(alarmFrequencyDaoMemoryImpl.isNeedSend(message, intervalSeconds));
		
		alarmFrequencyDaoMemoryImpl.upperLimitMap.put(System.currentTimeMillis() - 20 * 1000, "");
		Assert.assertFalse(alarmFrequencyDaoMemoryImpl.isNeedSend(message, intervalSeconds));

		alarmFrequencyDaoMemoryImpl.lastSendTimeMap.put(message, System.currentTimeMillis() - 1 * 1000);
		Assert.assertFalse(alarmFrequencyDaoMemoryImpl.isNeedSend(message, intervalSeconds));

	}

}