package io.leopard.monitor.forecast;


public interface ForecastService {

	/**
	 * 新增
	 * 
	 * @return
	 */
	boolean add(Forecast forecast);

	/**
	 * 是否已存
	 * 
	 * @param url
	 * @return
	 */
	boolean exist(String url);

	/**
	 * 预警
	 * 
	 * @param forecast
	 */
	void alarm(Forecast forecast);

}
