package io.leopard.test4j.mock.dao;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.memcache.Memcache;
import io.leopard.test4j.mock.transaction.MockTransactionModule;

import java.lang.reflect.Field;
import java.util.List;

public class InstanceDaoMemcachedImpl implements InstanceDao {

	@Override
	public <T> T instance(Class<T> clazz) {
		T bean = InstanceUtil.instantiateClass(clazz);
		DaoInstanceUtil.mockAllFields(bean);

		Memcache memcache = MockTransactionModule.getInstance(Memcache.class);
		List<Field> list = FieldUtil.listFields(bean, Memcache.class);
		for (Field field : list) {
			FieldUtil.setFieldValue(bean, field, memcache);
		}
		return bean;
	}

}
