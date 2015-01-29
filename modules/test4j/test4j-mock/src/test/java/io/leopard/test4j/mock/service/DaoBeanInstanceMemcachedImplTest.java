package io.leopard.test4j.mock.service;

import org.junit.Assert;
import org.junit.Test;

public class DaoBeanInstanceMemcachedImplTest {

	protected DaoBeanInstanceMemcachedImpl dao = new DaoBeanInstanceMemcachedImpl();

	@Test
	public void getImplClassName() {
		Assert.assertEquals("com.duowan.news.dao.memcached.UserDaoMemcachedImpl", dao.getImplClassName("com.duowan.news.dao.UserDao"));
	}

}