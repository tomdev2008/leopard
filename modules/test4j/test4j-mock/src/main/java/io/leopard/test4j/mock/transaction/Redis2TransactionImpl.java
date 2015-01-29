package io.leopard.test4j.mock.transaction;

import io.leopard.data4j.redis.Redis;


/**
 * redis基于jdbc的实现(部分接口的意思会有改变).
 * 
 * @author 阿海
 * 
 */
public class Redis2TransactionImpl extends RedisTransactionImpl implements Redis {

	@Override
	public Long hset(String key, String field, String value) {
		String oldValue = super.hget(key, field);
		Long result = super.hset(key, field, value);
		if (oldValue != null) {
			result = 0L;
		}
		return result;
	}

	@Override
	public Long zadd(String key, double score, String member) {
		Long num = super.zrank(key, member);
		Long result = super.zadd(key, score, member);
		if (num != null) {
			result = 0L;
		}
		return result;
	}
}
