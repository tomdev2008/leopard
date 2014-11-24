package io.leopard.test.api;

import io.leopard.data4j.cache.api.IDelete;

import java.util.Date;

import org.junit.Test;

public class DaoTesterMemcacheImplTest {

	protected DaoTesterMemcacheImpl daoTesterMemcacheImpl = new DaoTesterMemcacheImpl();

	public static interface UserDao extends IDelete<String, String> {

	}

	public static class UserDaoMemcacheImpl implements UserDao {

		@Override
		public boolean delete(String key, String opusername, Date lmodify) {
			
			return false;
		}

		@Override
		public String get(String key) {
			
			return null;
		}

		@Override
		public boolean add(String bean) {
			
			return false;
		}

	}

	@Test
	public void start() throws Exception {
		daoTesterMemcacheImpl.start(UserDaoMemcacheImpl.class);
	}

}