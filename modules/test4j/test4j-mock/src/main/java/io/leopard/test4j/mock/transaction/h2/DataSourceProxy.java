package io.leopard.test4j.mock.transaction.h2;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang.NotImplementedException;

public class DataSourceProxy implements DataSource {

	protected DataSource dataSource;

	private Connection conn;

	public DataSourceProxy(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		// System.out.println("this:" + this);
		if (conn == null) {
			conn = new ProxyConnection(dataSource.getConnection());
			// System.out.println("conn:" + conn);
		}
		return conn;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return dataSource.getConnection(username, password);
	}

	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new NotImplementedException();
	}

}
