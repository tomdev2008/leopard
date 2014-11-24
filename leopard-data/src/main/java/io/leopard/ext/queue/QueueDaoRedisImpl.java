package io.leopard.ext.queue;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.Json;
import io.leopard.commons.utility.NumberUtil;
import io.leopard.data4j.redis.RedisBase;

public class QueueDaoRedisImpl extends RedisBase implements QueueDao {

	private String key;

	public void setKey(String key) {
		this.key = key;
	}

	protected String getKey() {
		if (key == null) {
			throw new IllegalArgumentException("属性key未设置.");
		}
		return key;
	}

	@Override
	public int add(Object obj) {
		AssertUtil.assertNotNull(obj, "参数obj不能为空.");
		String json = Json.toJson(obj);
		String key = this.getKey();
		System.out.println("key:" + key + " json:" + json);
		Long result = this.redis.rpush(key, json);
		return NumberUtil.toInt(result);
	}

	@Override
	public String pop() {
		String json = this.redis.lpop(this.getKey());
		return json;
	}

	@Override
	public int count() {
		Long result = this.redis.llen(this.getKey());
		return NumberUtil.toInt(result);
	}
}
