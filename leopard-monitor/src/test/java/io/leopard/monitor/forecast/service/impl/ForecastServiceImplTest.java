package io.leopard.monitor.forecast.service.impl;

import io.leopard.monitor.forecast.Forecast;
import io.leopard.monitor.forecast.ForecastServiceImpl;
import io.leopard.test.mock.Assert;
import io.leopard.test.mock.Mock;

import org.junit.Test;

public class ForecastServiceImplTest {

	protected ForecastServiceImpl forecastServiceImpl = Mock.spy(this, ForecastServiceImpl.class);

	@Test
	public void add() {
		Assert.when(forecastServiceImpl).add(Mock.newInstance(Forecast.class));
	}

	@Test
	public void exists() {
		Assert.when(forecastServiceImpl).exist("xxx");
	}

	@Test
	public void alarm() {
		Forecast forecast = Mock.newInstance("{url:xxxx,count:2,time:0,methodName:xxx}", Forecast.class);
		Mock.doReturn().when(forecastServiceImpl).add(forecast);
		forecastServiceImpl.alarm(forecast);
		Mock.verify(forecastServiceImpl).add(forecast);
	}
}
