package io.leopard.monitor.forecast;

import io.leopard.data4j.memcache.MemcacheRedisImpl;
import io.leopard.data4j.redis.Redis;
import io.leopard.data4j.schema.LeopardBeanFactoryAware;

import org.springframework.beans.factory.BeanFactory;

public class ForecastUtil {

	private static Boolean forecast;

	public static Redis getRedis() {
		BeanFactory beanFactory = LeopardBeanFactoryAware.getBeanFactory();
		MemcacheRedisImpl memcache = null;
		try {
			memcache = beanFactory.getBean(MemcacheRedisImpl.class);
		}
		catch (Exception e) {
			throw new RuntimeException("找不到可用的Redis!!!");
		}
		return memcache.getRedis();
	}

	private static boolean isStartForecast() {
		boolean flag = true;
		try {
			ForecastUtil.getRedis();
		}
		catch (Exception e) {
			flag = false;
			System.err.println("找不到可用的MemcacheRedisImpl,未开启性能预警！");
		}
		return flag;
	}

	public static Boolean getForecast() {
		if (forecast == null) {
			forecast = isStartForecast();
		}
		return forecast;
	}

}
