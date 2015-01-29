package io.leopard.data4j.env;

import io.leopard.burrow.LeopardLei;

import org.springframework.core.io.Resource;

/**
 * 占位符
 * 
 * @author 阿海
 *
 */
public interface PropertyPlaceholderLei extends LeopardLei {

	Resource[] getResources(String env);
}
