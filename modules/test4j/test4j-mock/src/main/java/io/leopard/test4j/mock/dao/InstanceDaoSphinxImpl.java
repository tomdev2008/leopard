package io.leopard.test4j.mock.dao;


public class InstanceDaoSphinxImpl implements InstanceDao {

	@Override
	public <T> T instance(Class<T> clazz) {
		T bean = InstanceUtil.instantiateClass(clazz);
		DaoInstanceUtil.mockAllFields(bean);
		return bean;
		// throw new NotImplementedException();
	}
}
