package io.leopard.test4j.mock.dao;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.redis.Redis;
import io.leopard.test4j.mock.transaction.MockTransactionModule;
import io.leopard.test4j.mock.transaction.Redis2TransactionImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mockito.Mockito;

public class InstanceDaoVersionImpl implements InstanceDao {

	@Override
	public <T> T instance(Class<T> clazz) {
		T bean = InstanceUtil.instantiateClass(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.equals("$jacocoData")) {
				// 忽略Coverage插件信息.
				continue;
			}
			else if (DaoInstanceUtil.isSimpleType(field.getType())) {
				// 忽略基本类型
				continue;
			}
			else if (field.getType().equals(Redis.class)) {
				Redis redis = MockTransactionModule.getInstance(Redis2TransactionImpl.class);
				FieldUtil.setFieldValue(bean, field, redis);
				continue;
			}
			// System.out.println("fieldName:" + fieldName);
			Class<?> implClass = DaoInstanceUtil.getFullClass(field.getType(), fieldName);
			Object value = DaoInstanceUtil.newInstance(fieldName, implClass);
			value = Mockito.spy(value);
			FieldUtil.setFieldValue(bean, field, value);
		}

		{
			try {
				Method method = clazz.getMethod("setRedis", Redis.class);
				Redis redis = MockTransactionModule.getInstance(Redis2TransactionImpl.class);
				method.setAccessible(true);
				method.invoke(bean, redis);
			}
			catch (NoSuchMethodException e) {
				// 忽略setRedis不存在异常
			}
			catch (Exception e) {
				// e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			}

		}
		return bean;
	}

}
