package io.leopard.test4j.mock.service;

import org.junit.Assert;
import org.junit.Test;

public class BeanInstanceDaoImplTest {

	protected BeanInstanceDaoImpl dao = new BeanInstanceDaoImpl();

	public static class UserDaoImpl {

	}

	@Test
	public void instance() {
		// Assert.assertEquals("com.duowan.news.dao.cache.UserDaoCacheImpl", dao.getImplClassName("com.duowan.news.dao.UserDao"));
		try {
			dao.instance(UserDaoImpl.class);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void BeanInstanceDaoImpl() {
	
	}

}