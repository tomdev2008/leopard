package io.leopard.data.h2;

import io.leopard.data4j.jdbc.H2DataSourceFactory;

import org.junit.Assert;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class H2DataSourceFactoryTest {

	@Test
	public void createDataSource() {
		ComboPooledDataSource dataSource = (ComboPooledDataSource) H2DataSourceFactory.createDataSource("leopard");

		Assert.assertEquals("sa", dataSource.getUser());
	}

	@Test
	public void H2DataSourceFactory() {
		new H2DataSourceFactory();
	}

}