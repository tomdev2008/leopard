package io.leopard.monitor.forecast;

import io.leopard.data4j.redis.Redis;

import org.apache.log4j.Logger;

public class ForecastDaoRedisImpl implements ForecastDao {

	Logger logger = Logger.getLogger(this.getClass());

	private Redis redis;

	protected Redis getRedis() {
		if (redis == null) {
			this.redis = ForecastUtil.getRedis();
		}
		return this.redis;
	}

	private final String FORECAST_KEY = "forecast";

	@Override
	public boolean add(Forecast forecast) {
		this.getRedis().sadd(FORECAST_KEY, forecast.getUrl());
		return true;
	}

	@Override
	public boolean exist(String url) {
		return this.getRedis().sismember(FORECAST_KEY, url);
	}

}
