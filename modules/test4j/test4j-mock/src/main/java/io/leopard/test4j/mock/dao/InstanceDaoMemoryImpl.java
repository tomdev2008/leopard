package io.leopard.test4j.mock.dao;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.data4j.memdb.Memdb;
import io.leopard.test4j.mock.transaction.MemdbTransactionImpl;
import io.leopard.test4j.mock.transaction.MockTransactionModule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.reflect.FieldUtils;

public class InstanceDaoMemoryImpl implements InstanceDao {

	@Override
	public <T> T instance(Class<T> clazz) {
		T bean = InstanceUtil.instantiateClass(clazz);
		DaoInstanceUtil.mockAllFields(bean);

		Field field = FieldUtils.getField(clazz, "memdb", true);
		if (field != null) {
			Memdb memoryDb = MockTransactionModule.getInstance(MemdbTransactionImpl.class);
			FieldUtil.setFieldValue(bean, field, memoryDb);
		}
		this.invokeInitMethod(clazz, bean);
		return bean;
	}

	protected <T> void invokeInitMethod(Class<T> clazz, T bean) {
		try {
			Method method = clazz.getMethod("init");
			PostConstruct postConstruct = method.getAnnotation(PostConstruct.class);
			if (postConstruct != null) {
				method.invoke(bean);
			}
		}
		catch (NoSuchMethodException e) {

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
