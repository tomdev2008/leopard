package io.leopard.test4j.mock.dao;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.data4j.memcache.Memcache;
import io.leopard.data4j.redis.Redis;
import io.leopard.test4j.mock.transaction.MockTransactionModule;

import java.lang.reflect.Field;

import org.mockito.Mockito;
import org.springframework.cache.CacheManager;

public class InstanceDaoCacheImpl implements InstanceDao {

	/**
	 * 是否要忽略的类型.
	 * 
	 * @param type
	 * @return
	 */
	protected boolean isIgnoreType(Class<?> type) {
		if (DaoInstanceUtil.isSimpleType(type)) {
			// 忽略基本类型
			return true;
		}
		else if (type.equals(CacheManager.class)) {
			// 忽略CacheManager
			return true;
		}
		// else if (type.equals(Counter.class)) {
		// // 忽略Counter
		// return true;
		// }
		return false;
	}

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
			else if (isIgnoreType(field.getType())) {
				continue;
			}

			else if (field.getType().equals(Memcache.class)) {
				Memcache memcache = MockTransactionModule.getInstance(Memcache.class);
				FieldUtil.setFieldValue(bean, field, memcache);
				continue;
			}
			else if (field.getType().equals(Jdbc.class)) {
				Jdbc jdbc = MockTransactionModule.getInstance(Jdbc.class);
				// FieldUtil.setFieldValue(bean, field, jdbc);
				continue;
			}
			else if (field.getType().equals(Redis.class)) {
				Redis redis = MockTransactionModule.getInstance(Redis.class);
				FieldUtil.setFieldValue(bean, field, redis);
				continue;
			}
			else {
				// System.out.println("fieldName:" + fieldName);
				this.mockOther(bean, field);
			}
		}
		return bean;
	}

	protected void mockOther(Object bean, Field field) {
		String fieldName = field.getName();
		Class<?> implClass = DaoInstanceUtil.getFullClass(field.getType(), fieldName);
		Object value = DaoInstanceUtil.newInstance(fieldName, implClass);
		value = Mockito.spy(value);
		FieldUtil.setFieldValue(bean, field, value);
	}
}
