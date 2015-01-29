package io.leopard.test4j.mock.service;

import org.junit.Assert;
import org.junit.Test;

public class DaoBeanInstanceMysqlImplTest {
	protected DaoBeanInstanceMysqlImpl dao = new DaoBeanInstanceMysqlImpl();

	@Test
	public void getImplClassName() {
		Assert.assertEquals("com.duowan.news.dao.mysql.UserDaoMysqlImpl", dao.getImplClassName("com.duowan.news.dao.UserDao"));
	}

}