package io.leopard.test4j.mock.transaction.redis;

import io.leopard.burrow.lang.Context;
import io.leopard.data4j.redis.Redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import com.google.inject.Inject;

public class TransactionTestnbImpl extends Transaction implements Context {

	@Inject
	private Redis redis;

	private List<Object> statusList = new ArrayList<Object>();

	public void clearResponseStatusList() {
		statusList.clear();
	}

	@Override
	public synchronized Response<Double> zincrby(String key, double score, String member) {
		redis.zincrby(key, score, member);
		return null;
	}

	@Override
	public synchronized Response<Long> hset(String key, String field, String value) {
		redis.hset(key, field, value);
		return null;
	}

	@Override
	public Response<Long> expire(String key, int seconds) {
		redis.expire(key, seconds);
		return null;
	}

	@Override
	public Response<Long> zadd(String key, double score, String member) {
		redis.zadd(key, score, member);
		return null;
	}

	// @Override
	// public Response<Long> zrem(String key, String member) {
	// Long num = redis.zrem(key, member);
	// statusList.add(num);
	// return null;
	// }

	@Override
	public Response<Long> setrange(String key, long offset, String value) {
		redis.setrange(key, offset, value);
		return null;
	}

	// @Override
	// public Response<Long> hdel(String key, String field) {
	// redis.hdel(key, field);
	// return null;
	// }

	@Override
	public Response<Long> del(String... keys) {
		redis.del(keys);
		return null;
	}

	@Override
	public List<Object> exec() {
		return this.statusList;
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}

}
