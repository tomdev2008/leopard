package io.leopard.test4j.mock.transaction.h2;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.google.inject.Provider;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceProvider implements Provider<DataSource> {

	// private static boolean autoCommit = true;
	//
	// public static void setAutoCommit(boolean autoCommit) {
	// DataSourceProvider.autoCommit = autoCommit;
	// }

	@Override
	public DataSource get() {
		DataSource dataSource = this.getH2();
		DataSourceProxy dataSourceProxy = new DataSourceProxy(dataSource) {
			@Override
			public Connection getConnection() throws SQLException {
				Connection conn = super.getConnection();
				conn.setAutoCommit(false);
				return conn;
			}
		};
		return dataSourceProxy;
	}

	private DataSource getH2() {
		String project = new TableInfoFileImpl().getDatabaseName();// EnvUtil.getDuowanProjectNo();
		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		String jdbcUrl = "jdbc:h2:/log/resin/h2/" + project;
		jdbcUrl += ";lock_mode=0";
		// jdbcUrl += ";MVCC=true";
		dataSource.setJdbcUrl(jdbcUrl);// MVCC=true
		try {
			dataSource.setDriverClass("org.h2.Driver");
		}
		catch (PropertyVetoException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		dataSource.setUser("sa");
		dataSource.setPassword("");

		{
			dataSource.setInitialPoolSize(1);
			dataSource.setMinPoolSize(1);
			dataSource.setMaxPoolSize(3);
			dataSource.setAcquireIncrement(1);
			// dataSource.setAcquireRetryAttempts(1);
			// dataSource.setMaxIdleTime(7200);
			// dataSource.setMaxStatements(0);
		}

		return dataSource;
	}

}
