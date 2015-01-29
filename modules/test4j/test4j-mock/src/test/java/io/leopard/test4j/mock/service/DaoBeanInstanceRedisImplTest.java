package io.leopard.test4j.mock.service;

import org.junit.Assert;
import org.junit.Test;

public class DaoBeanInstanceRedisImplTest {

	protected DaoBeanInstanceRedisImpl dao = new DaoBeanInstanceRedisImpl();

	@Test
	public void getImplClassName() {
		Assert.assertEquals("com.duowan.news.dao.redis.UserDaoRedisImpl", dao.getImplClassName("com.duowan.news.dao.UserDao"));
	}
}