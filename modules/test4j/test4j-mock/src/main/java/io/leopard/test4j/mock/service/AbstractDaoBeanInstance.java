package io.leopard.test4j.mock.service;

import io.leopard.test4j.mock.dao.DaoInstanceUtil;

public abstract class AbstractDaoBeanInstance implements DaoBeanInstance {

	protected abstract String getImplClassName(String className);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T instance(Class<T> clazz) {
		String className = clazz.getName();
		String implClassName = this.getImplClassName(className);

		Class<T> implClazz;
		try {
			implClazz = (Class<T>) Class.forName(implClassName);
		}
		catch (ClassNotFoundException e) {
			return null;
		}
		String beanName = implClazz.getSimpleName();

		System.err.println("implClassName:" + implClazz.getName());
		return DaoInstanceUtil.newInstance(beanName, implClazz);

	}

}
