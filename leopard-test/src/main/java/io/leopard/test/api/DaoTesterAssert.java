package io.leopard.test.api;

import io.leopard.data4j.cache.api.IAdd;
import io.leopard.data4j.cache.api.IDelete;
import io.leopard.data4j.cache.api.IGet;
import io.leopard.test.mock.Mock;

import java.util.Date;

import org.apache.commons.lang.NotImplementedException;

public class DaoTesterAssert {

	@SuppressWarnings("unchecked")
	public static void start(Object dao) throws Exception {
		if (dao instanceof IDelete) {
			delete((IDelete<Object, Object>) dao);
		}
		else if (dao instanceof io.leopard.data4j.cache.api.uid.IDelete) {
			delete((io.leopard.data4j.cache.api.uid.IDelete<Object, Object>) dao);
		}
		else if (dao instanceof IGet) {
			get((IGet<Object, Object>) dao);
		}
		else if (dao instanceof IAdd) {
			add((IAdd<Object>) dao);
		}
		else {
			// 忽略没有实现标准接口
		}
	}

	protected static void add(IAdd<Object> dao) {
		Class<?> beanClazz = ApiClassUtil.getBeanClass(dao);
		Object bean;
		if (beanClazz.getName().startsWith("java.lang")) {
			bean = ApiKeyUtil.getDefaultValue(beanClazz);
		}
		else {
			bean = Mock.newInstance(beanClazz);
		}
		try {
			dao.add(bean);
		}
		catch (NotImplementedException e) {

		}
	}

	protected static void get(IGet<Object, Object> dao) {
		add(dao);

		Class<?> keyClazz = ApiClassUtil.getKeyClass(dao);
		Object key = ApiKeyUtil.getKey(keyClazz);
		try {
			dao.get(key);
		}
		catch (NotImplementedException e) {

		}
		System.out.println("get:" + dao + " keyClazz:" + keyClazz);
	}

	protected static void delete(io.leopard.data4j.cache.api.uid.IDelete<Object, Object> dao) {
		get(dao);
		Class<?> keyClazz = ApiClassUtil.getKeyClass(dao);
		Object key = ApiKeyUtil.getKey(keyClazz);
		try {
			dao.delete(key, 1, new Date());
		}
		catch (NotImplementedException e) {

		}
		System.out.println("get:" + dao + " keyClazz:" + keyClazz);
	}

	protected static void delete(IDelete<Object, Object> dao) {
		get(dao);
		Class<?> keyClazz = ApiClassUtil.getKeyClass(dao);
		Object key = ApiKeyUtil.getKey(keyClazz);
		try {
			dao.delete(key, "opusername", new Date());
		}
		catch (NotImplementedException e) {

		}
		System.out.println("get:" + dao + " keyClazz:" + keyClazz);
	}
}
