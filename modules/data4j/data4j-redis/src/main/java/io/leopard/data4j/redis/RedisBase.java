package io.leopard.data4j.redis;

import io.leopard.burrow.lang.ContextImpl;

import org.apache.commons.lang.StringUtils;

public class RedisBase extends ContextImpl implements RedisList {

	protected Redis redis;
	protected Redis[] redisList;
	protected String server;
	protected int maxActive;
	protected boolean log;

	private RedisImpl redisImpl;
	private RedisLog4jImpl redisLog4jImpl;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public Redis getRedis() {
		return redis;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public void setRedisList(Redis[] redisList) {
		this.redisList = redisList;
		this.redis = redisList[0];
	}

	public int getRedisSize() {
		return this.redisList.length;
	}

	@Override
	public void init() {
		super.init();

		if (StringUtils.isNotEmpty(server)) {
			this.redisImpl = new RedisImpl(server, maxActive, 0, false, 0);
			// redisImpl.setServer(server);
			// redisImpl.setMaxActive(maxActive);
			redisImpl.init();
			if (log) {
				this.redisLog4jImpl = new RedisLog4jImpl();
				this.redisLog4jImpl.setRedis(this.redisImpl);
				this.redisLog4jImpl.init();
			}
			if (log) {
				this.redis = this.redisLog4jImpl;
			}
			else {
				this.redis = this.redisImpl;
			}
		}
	}

	@Override
	public void destroy() {
		if (redisImpl != null) {
			this.redisImpl.destroy();
		}
		if (redisLog4jImpl != null) {
			this.redisLog4jImpl.destroy();
		}
		super.destroy();
	}

	@Override
	public Redis[] getRedisList() {
		return redisList;
	}
}
