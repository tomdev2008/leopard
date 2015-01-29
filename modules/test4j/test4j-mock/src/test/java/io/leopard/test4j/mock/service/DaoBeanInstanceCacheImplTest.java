package io.leopard.test4j.mock.service;

import org.junit.Assert;
import org.junit.Test;

public class DaoBeanInstanceCacheImplTest {

	protected DaoBeanInstanceCacheImpl dao = new DaoBeanInstanceCacheImpl();

	@Test
	public void getImplClassName() {
		Assert.assertEquals("com.duowan.news.dao.cache.UserDaoCacheImpl", dao.getImplClassName("com.duowan.news.dao.UserDao"));
	}

}