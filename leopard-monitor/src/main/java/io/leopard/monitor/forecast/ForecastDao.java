package io.leopard.monitor.forecast;

import io.leopard.data4j.cache.api.IAdd;

public interface ForecastDao extends IAdd<Forecast>{

	boolean add(Forecast forcast);
	
	boolean exist(String url);


}
