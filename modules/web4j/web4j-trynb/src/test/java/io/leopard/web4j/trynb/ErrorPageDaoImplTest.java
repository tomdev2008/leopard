package io.leopard.web4j.trynb;

import org.junit.Assert;
import org.junit.Test;

public class ErrorPageDaoImplTest {

	protected ErrorPageDaoImpl errorPageDao = new ErrorPageDaoImpl();

	@Test
	public void add() {
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/user/");
			errorPageDao.add(errorConfig);
		}
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/user/info.do");
			errorPageDao.add(errorConfig);
		}

		Assert.assertNotNull(this.errorPageDao.findErrorInfo("/user/index.do"));
		Assert.assertNull(this.errorPageDao.findErrorInfo("/group/index.do"));
	}

}