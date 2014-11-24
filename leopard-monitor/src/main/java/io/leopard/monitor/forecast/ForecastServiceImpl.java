package io.leopard.monitor.forecast;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ForecastServiceImpl implements ForecastService {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired(required = false)
	private ForecastDao forecastDao;

	@Override
	public boolean add(Forecast forecast) {
		return forecastDao.add(forecast);
	}

	@Override
	public boolean exist(String url) {
		return forecastDao.exist(url);
	}

	@Override
	public void alarm(Forecast forecast) {
		if (forecast.getCount() >= ForecastConstant.VISITS_COUNT) {
			this.add(forecast);
		}
		if ((forecast.getCount() >= ForecastConstant.VISITS_COUNT) || this.exist(forecast.getUrl())) {
			logger.error(forecast.getMethodName() + "接口耗时超过" + ForecastConstant.VISITS_TIME + "ms,实际耗时为：" + forecast.getTime() + "ms,请尽快处理！");
		}
	}
}
