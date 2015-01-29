package io.leopard.test4j.mock.dao;

/**
 * dao实例化.
 * 
 * @author 阿海
 * 
 */
public interface InstanceDao {

	<T> T instance(Class<T> clazz);
}
