package io.leopard.monitor;

import io.leopard.data4j.redis.Redis;
import io.leopard.schema.model.MonitorConfig;
import io.leopard.schema.model.RedisInfo;
import io.leopard.test.mock.Mock;
import io.leopard.test4j.mock.LeopardMockito;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RedisMonitorTest {

	RedisMonitor monitor = Mockito.spy(new RedisMonitor());

	@Test
	public void getRedis() {
		Assert.assertNotNull(this.monitor.getRedis("127.0.0.1:6379"));
		Assert.assertNotNull(this.monitor.getRedis("127.0.0.1:6379"));
	}

	@Test
	public void destroy() {
		Assert.assertNotNull(this.monitor.getRedis("127.0.0.1:6379"));
		this.monitor.destroy();
	}

	@Test
	public void monitor() {
		List<RedisInfo> redisInfoList = new ArrayList<RedisInfo>();
		{
			RedisInfo redisInfo = new RedisInfo();
			redisInfo.setMaxMemory("1M");
			redisInfo.setServer("127.0.0.1:6379");
			redisInfoList.add(redisInfo);
		}

		MonitorConfig monitorConfig = Mockito.mock(MonitorConfig.class);
		Mockito.when(monitorConfig.getRedisInfoList()).thenReturn(redisInfoList);
		LeopardMockito.setProperty(monitor, monitorConfig);

		Redis redis = Mockito.mock(Redis.class);
		Mock.doReturn(redis).when(monitor).getRedis("127.0.0.1:6379");
		monitor.monitor();

	}

	@Test
	public void check() {

		RedisInfo redisInfo = new RedisInfo();
		redisInfo.setMaxMemory("1M");
		redisInfo.setServer("127.0.0.1:6379");
		Redis redis = Mockito.mock(Redis.class);
		Mock.doReturn(redis).when(monitor).getRedis("127.0.0.1:6379");
		this.monitor.check(redisInfo);
	}

	@Test
	public void RedisMonitor() {
	
	}
}