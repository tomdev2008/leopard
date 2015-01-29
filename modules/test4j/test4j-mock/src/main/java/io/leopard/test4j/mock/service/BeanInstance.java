package io.leopard.test4j.mock.service;

/**
 * Bean实例化.
 * 
 * @author 阿海
 * 
 */
public interface BeanInstance {

	<T> T instance(Class<T> clazz);

}
