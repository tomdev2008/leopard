package io.leopard.test4j.mock.transaction;

import org.junit.Assert;
import org.junit.Test;

public class DataSourceTransactionManagerTest {

	@Test
	public void getDataSource() {
		DataSourceTransactionManager manager = new DataSourceTransactionManager();
		Assert.assertNotNull(manager.getDataSource());
	}

}