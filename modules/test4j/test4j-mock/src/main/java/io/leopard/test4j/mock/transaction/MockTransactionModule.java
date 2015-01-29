package io.leopard.test4j.mock.transaction;

import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.data4j.memcache.Memcache;
import io.leopard.data4j.redis.Redis;
import io.leopard.test4j.jdbc.JdbcH2Impl;
import io.leopard.test4j.mock.transaction.h2.DataSourceProvider;
import io.leopard.test4j.mock.transaction.h2.service.H2Dao;
import io.leopard.test4j.mock.transaction.h2.service.H2DaoImpl;
import io.leopard.test4j.mock.transaction.redis.JedisTestnbImpl;
import io.leopard.test4j.mock.transaction.redis.TransactionTestnbImpl;

import javax.sql.DataSource;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MockTransactionModule implements Module {

	private static Injector injector;

	@Override
	public void configure(Binder binder) {
		binder.bind(Redis.class).to(Redis2TransactionImpl.class).in(Scopes.SINGLETON);
		binder.bind(Memcache.class).to(MemcacheTransactionImpl.class).in(Scopes.SINGLETON);
		binder.bind(Jdbc.class).to(JdbcH2Impl.class).in(Scopes.SINGLETON);
		binder.bind(H2Dao.class).to(H2DaoImpl.class).in(Scopes.SINGLETON);

		binder.bind(DataSource.class).toProvider(DataSourceProvider.class).in(Scopes.SINGLETON);

		binder.bind(JedisTestnbImpl.class).toInstance(new JedisTestnbImpl());
		binder.bind(TransactionTestnbImpl.class).toInstance(new TransactionTestnbImpl());
	}

	public static <T> T getInstance(Class<T> clazz) {
		if (injector == null) {
			initInjector();
		}
		return injector.getInstance(clazz);
	}

	protected static synchronized Injector initInjector() {
		if (injector == null) {
			injector = Guice.createInjector(new MockTransactionModule());
		}
		return injector;
	}
}
