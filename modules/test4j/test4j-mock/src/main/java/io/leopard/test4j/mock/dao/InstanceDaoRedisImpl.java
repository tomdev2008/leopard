package io.leopard.test4j.mock.dao;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.redis.Redis;
import io.leopard.test4j.mock.transaction.MockTransactionModule;
import io.leopard.test4j.mock.transaction.Redis2TransactionImpl;

import java.lang.reflect.Field;

import org.apache.commons.lang.reflect.FieldUtils;

public class InstanceDaoRedisImpl implements InstanceDao {

	@Override
	public <T> T instance(Class<T> clazz) {
		T bean = InstanceUtil.instantiateClass(clazz);
		DaoInstanceUtil.mockAllFields(bean);

		Field field = FieldUtils.getField(clazz, "redis", true);
		if (field != null) {
			this.mockRedis(bean, field);
		}

		if (true) {
			Redis redis = MockTransactionModule.getInstance(Redis2TransactionImpl.class);
			Field redisListField = FieldUtils.getField(clazz, "redisList", true);
			if (redisListField != null) {
				FieldUtil.setFieldValue(bean, redisListField, new Redis[] { redis });
			}
		}

		return bean;
	}

	protected void mockRedis(Object bean, Field field) {
		Redis redis = MockTransactionModule.getInstance(Redis2TransactionImpl.class);
		FieldUtil.setFieldValue(bean, field, redis);
	}

}
