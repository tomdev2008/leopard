package io.leopard.test4j.mock.transaction;

import io.leopard.autounit.unitdb.H2Util;

import javax.sql.DataSource;

public class DataSourceTransactionManager extends org.springframework.jdbc.datasource.DataSourceTransactionManager {

	private static final long serialVersionUID = 1L;

	public DataSourceTransactionManager() {
		// DataSource dataSource = MockTransactionModule.getInstance(DataSource.class);
		DataSource dataSource = H2Util.createDataSource("leopard");
		super.setDataSource(dataSource);
	}

	public DataSource getDataSource() {
		// System.out.println("getDataSource:");
		// new Exception("getDataSource:").printStackTrace();
		return super.getDataSource();
	}

}
