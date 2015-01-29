package io.leopard.test4j.mock.service;

/**
 * DAO bean实例化.
 * 
 * @author 阿海
 * 
 */
public interface DaoBeanInstance {
	<T> T instance(Class<T> clazz);
}
