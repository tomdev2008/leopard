package io.leopard.test4j.mock.service;

import java.util.ArrayList;
import java.util.List;

public class BeanInstanceDaoImpl implements BeanInstance {

	private List<DaoBeanInstance> list = new ArrayList<DaoBeanInstance>();

	public BeanInstanceDaoImpl() {
		list.add(new DaoBeanInstanceCacheImpl());
		list.add(new DaoBeanInstanceMysqlImpl());
		list.add(new DaoBeanInstanceMemcachedImpl());
		list.add(new DaoBeanInstanceRedisImpl());
		list.add(new DaoBeanInstanceImpl());
	}

	@Override
	public <T> T instance(Class<T> clazz) {
		for (DaoBeanInstance daoBeanInstance : list) {
			T bean = daoBeanInstance.instance(clazz);
			if (bean != null) {
				return bean;
			}
		}
		throw new RuntimeException("找不到DAO[" + clazz.getName() + "]对应的实现类.");
	}

}
