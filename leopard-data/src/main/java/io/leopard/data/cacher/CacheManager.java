package io.leopard.data.cacher;

import io.leopard.burrow.lang.Json;
import io.leopard.data4j.memcache.IMemcache;
import io.leopard.data4j.memcache.Memcache;

public abstract class CacheManager {

	private static final ThreadLocal<String> CURRENT_MEMCACHE_KEY = new ThreadLocal<String>();

	private final int expireSeconds;

	public CacheManager(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	private final ProxyFactory proxyFactory = new ProxyFactory() {
		@Override
		public Object invoke(Object bean, Object[] args) {
			Object result = CacheManager.this.invoke(args);
			if (result != null) {
				String memcacheKey = CURRENT_MEMCACHE_KEY.get();
				String json = Json.toJson(result);
				Memcache memcache = ((IMemcache) bean).getMemcache();
				memcache.put(memcacheKey, json, expireSeconds);
			}
			return result;
		}
	};

	public abstract Object invoke(Object[] args);

	// public abstract String getMemcacheKey();

	public <T> T execute(T memcacheImpl, String memcacheKey) {
		CURRENT_MEMCACHE_KEY.set(memcacheKey);
		// if (true) {
		// return memcacheImpl;
		// }
		@SuppressWarnings("unchecked")
		T proxy = proxyFactory.getInstance(memcacheImpl);
		return proxy;
		// return memcacheImpl;
	}

}
