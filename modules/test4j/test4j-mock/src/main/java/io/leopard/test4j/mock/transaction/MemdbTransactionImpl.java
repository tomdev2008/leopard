package io.leopard.test4j.mock.transaction;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.lang.Json;
import io.leopard.data4j.memcache.Memcache;
import io.leopard.data4j.memdb.Memdb;

import org.apache.commons.lang.NotImplementedException;

import com.google.inject.Inject;

/**
 * MeoryDB接口JDBC实现(为了测试时可以事务回滚).
 * 
 * @author 阿海
 * 
 */
public class MemdbTransactionImpl extends ContextImpl implements Memdb {

	@Inject
	protected Memcache memcache;

	@Override
	public boolean set(String key, String value) {
		boolean sucess = this.memcache.add(key, value);
		// System.out.println("sucess:" + sucess + " key:" + key);
		return sucess;
	}

	@Override
	public String get(String key) {
		return memcache.get(key);
	}

	@Override
	public boolean remove(String key) {
		AssertUtil.assertNotNull(key, "参数key不能为null");
		return memcache.remove(key.toString());
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		String json = this.get(key);
		return Json.toObject(json, clazz);
	}

	@Override
	public long dbSize() {
		throw new NotImplementedException();
	}

}
