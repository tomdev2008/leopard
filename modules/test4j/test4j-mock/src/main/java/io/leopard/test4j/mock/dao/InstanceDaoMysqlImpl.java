package io.leopard.test4j.mock.dao;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.jdbc.Jdbc;
import io.leopard.test4j.mock.transaction.MockTransactionModule;

import java.lang.reflect.Field;

import org.apache.commons.lang.reflect.FieldUtils;

public class InstanceDaoMysqlImpl implements InstanceDao {

	@Override
	public <T> T instance(Class<T> clazz) {
		// System.out.println("instance clazz:" + clazz);
		T bean = InstanceUtil.instantiateClass(clazz);
		DaoInstanceUtil.mockAllFields(bean);

		Field field = FieldUtils.getField(clazz, "jdbc", true);
		if (field != null) {
			Jdbc jdbc = MockTransactionModule.getInstance(Jdbc.class);
			FieldUtil.setFieldValue(bean, field, jdbc);
		}
		return bean;
	}

}
