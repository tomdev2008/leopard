package io.leopard.data4j.jdbc;

import io.leopard.data4j.jdbc.ProxyDataSource;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ProxyDataSourceTest {
	// ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource)
	// H2DataSourceFactory.createDataSource("leopard");

	protected ProxyDataSource proxyDataSource = newInstance();

	protected ProxyDataSource newInstance() {
		ProxyDataSource proxyDataSource = Mockito.spy((new ProxyDataSource(new ComboPooledDataSource())));
		// ProxyDataSource proxyDataSource = Mockito.mock(ProxyDataSource);
		Mockito.doNothing().when(proxyDataSource).initJdbcConnectionListener();
		return proxyDataSource;
	}

	@Test
	public void close() {
		proxyDataSource.close();
	}

	@Test
	public void ProxyDataSource() {

	}

	@Test
	public void getLogWriter() throws SQLException {
		proxyDataSource.getLogWriter();
	}

	@Test
	public void setLogWriter() throws SQLException {
		proxyDataSource.setLogWriter(null);
	}

	@Test
	public void setLoginTimeout() throws SQLException {
		proxyDataSource.setLoginTimeout(1);
	}

	@Test
	public void getLoginTimeout() throws SQLException {
		proxyDataSource.getLoginTimeout();
	}

	@Test
	public void unwrap() throws SQLException {
		try {
			proxyDataSource.unwrap(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (NotImplementedException e) {

		}
	}

	@Test
	public void isWrapperFor() throws SQLException {
		try {
			proxyDataSource.isWrapperFor(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (NotImplementedException e) {

		}
	}

	@Test
	public void getConnection() throws SQLException {
		// proxyDataSource.getConnection();
	}

	@Test
	public void parseHost() {
		String host = this.proxyDataSource.parseHost("jdbc:mysql://leopard.game.yy.com:leopard");
		Assert.assertEquals("leopard.game.yy.com", host);
	}

	@Test
	public void printInfo() {
		// proxyDataSource.printInfo();
	}

	@Test
	public void getJdbcUrl() {
		String url = ProxyDataSource.getJdbcUrl("host", 3306, "leopard");
		// System.out.println("url:" + url);
		Assert.assertEquals("jdbc:mysql://host:3306/leopard?useUnicode=true&characterEncoding=UTF8", url);
	}

	@Test
	public void createDataSource() throws PropertyVetoException {
		String jdbcUrl = ProxyDataSource.getJdbcUrl("host", 3306, "leopard");
		ProxyDataSource.createDataSource(jdbcUrl, "user", "password", 10);
	}

}