package io.leopard.test4j.mock.service;

import org.junit.Assert;
import org.junit.Test;

public class DaoBeanInstanceImplTest {

	protected DaoBeanInstanceImpl dao = new DaoBeanInstanceImpl();

	@Test
	public void getImplClassName() {
		Assert.assertEquals("com.duowan.news.dao.impl.UserDaoImpl", dao.getImplClassName("com.duowan.news.dao.UserDao"));
	}

}