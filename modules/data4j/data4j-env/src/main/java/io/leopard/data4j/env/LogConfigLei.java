package io.leopard.data4j.env;

import io.leopard.burrow.LeopardLei;

import org.springframework.core.io.Resource;

/**
 * 日志配置.
 * 
 * @author 阿海
 *
 */
public interface LogConfigLei extends LeopardLei {

	void configure();

	Resource getResource(String env);
}
