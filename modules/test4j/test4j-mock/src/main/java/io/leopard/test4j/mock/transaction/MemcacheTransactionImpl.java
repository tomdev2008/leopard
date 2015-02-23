package io.leopard.test4j.mock.transaction;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.util.NumberUtil;
import io.leopard.memcache.Memcache;
import io.leopard.redis.Redis;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

/**
 * memcache接口JDBC实现(为了测试时可以事务回滚).
 * 
 * @author 阿海
 * 
 */
public class MemcacheTransactionImpl extends ContextImpl implements Memcache {

	@Inject
	protected Redis redis;

	@Override
	public boolean remove(String key) {
		Long result = redis.del(key);
		return NumberUtil.toBool(result);
	}

	@Override
	public boolean add(String key, String value) {
		int seconds = 60 * 60 * 24 * 30;
		return this.add(key, value, seconds);
	}

	@Override
	public boolean put(String key, String value) {
		int seconds = 60 * 60 * 24 * 30;
		return this.put(key, value, seconds);
	}

	@Override
	public boolean add(String key, String value, int seconds) {
		Long result = redis.setnx(key, value);
		boolean success = NumberUtil.toBool(result);
		if (success) {
			this.redis.expire(key, seconds);
		}
		return success;
	}

	@Override
	public boolean put(String key, String value, int seconds) {
		AssertUtil.assertNotEmpty(key, "参数key不能为空.");
		AssertUtil.assertNotNull(value, "参数value不能为null.");
		redis.set(key, value, seconds);
		return true;
	}

	@Override
	public boolean put(String key, int value) {
		return this.put(key, Integer.toString(value));
	}

	@Override
	public boolean put(String key, int value, int seconds) {
		return this.put(key, Integer.toString(value), seconds);
	}

	@Override
	public String get(String key) {
		return redis.get(key);
	}

	// @Override
	// public <BEAN> BEAN get(String key, Class<BEAN> clazz) {
	// String json = this.get(key);
	// return Json.toObject(json, clazz);
	// }

	@Override
	public List<String> mget(String[] keys) {
		List<String> list = new ArrayList<String>();
		for (String key : keys) {
			String value = this.get(key);
			list.add(value);
		}
		return list;
	}

	// @Override
	// public <BEAN> List<BEAN> mget(String[] keys, Class<BEAN> clazz) {
	// List<String> jsonList = this.mget(keys);
	// return Json.toObject(jsonList, clazz);
	// }

	// @Override
	// public boolean putObject(String key, Object obj) {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public boolean putObject(String key, Object obj, int seconds) {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public Object getObject(String key) {
	// throw new NotImplementedException();
	// }

	@Override
	public boolean flushAll() {
		return this.redis.flushDB();
		// throw new RuntimeException("no impl.");
	}

	@Override
	public long incr(String key) {
		return this.addOrIncr(key, 1);
		// String sql = "update " + TABLE + " set value=value+1 where `key`=?;";
		// StatementParameter param = new StatementParameter();
		// param.setString(key);
		// boolean success = super.updateForBoolean(sql, param);
		// if (!success) {
		// throw new RuntimeException("memcache的key不存在.");
		// }
		// return this.getInt(key);
	}

	@Override
	public long addOrIncr(String key, long num) {
		return redis.incrBy(key, num);
	}

	@Override
	public int getInt(String key) {
		String value = this.get(key);
		if (value == null) {
			return -1;
		}
		else {
			return Integer.parseInt(value);
		}
	}

}
