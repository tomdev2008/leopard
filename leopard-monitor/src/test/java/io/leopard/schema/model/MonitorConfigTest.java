package io.leopard.schema.model;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class MonitorConfigTest {

	@Test
	public void MonitorConfig() {
		MonitorConfig config = new MonitorConfig();
		config.setAlarmInfo(new AlarmInfo());
		Assert.assertNotNull(config.getAlarmInfo());
		config.setEnable(true);
		Assert.assertTrue(config.isEnable());
		config.setBaseInfo(new BaseInfo());
		Assert.assertNotNull(config.getBaseInfo());
		config.setNotifierList(new ArrayList<Notifier>());
		Assert.assertNotNull(config.getNotifierList());
		config.setRedisInfoList(new ArrayList<RedisInfo>());
		Assert.assertNotNull(config.getRedisInfoList());

		config.addNotifier(new Notifier());
		config.addRedisInfo(new RedisInfo());

	}

}