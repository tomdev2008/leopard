package io.leopard.test4j.mock.transaction.redis;

import io.leopard.burrow.lang.Context;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.inject.Inject;

public class JedisTestnbImpl extends Jedis implements Context {

	@Inject
	private TransactionTestnbImpl transactionTestnbImpl;

	public void setTransactionTestnbImpl(TransactionTestnbImpl transactionTestnbImpl) {
		this.transactionTestnbImpl = transactionTestnbImpl;
	}

	public JedisTestnbImpl() {
		this("");
	}

	public JedisTestnbImpl(String host) {
		super(host);
	}

	@Override
	public Transaction multi() {
		transactionTestnbImpl.clearResponseStatusList();
		return transactionTestnbImpl;
	}

	@Override
	public String flushDB() {
		return null;
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}

}
