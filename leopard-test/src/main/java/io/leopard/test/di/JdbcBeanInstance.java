package io.leopard.test.di;

import io.leopard.data4j.jdbc.JdbcMysqlImpl;
import io.leopard.data4j.jdbc.MysqlDsnDataSource;

import java.util.Map;

public class JdbcBeanInstance implements IBeanInstance {

	@Override
	public Object instance(String beanName) {
		Map<String, String> map = DataSourceUtil.getJdbc(beanName);

		MysqlDsnDataSource dataSource = new MysqlDsnDataSource();
		dataSource.setUrl(map.get("url"));
		dataSource.setUser(map.get("username"));
		dataSource.setPassword(map.get("password"));

		dataSource.init();

		// System.err.println("instance:" + beanName);
		JdbcMysqlImpl jdbc = new JdbcMysqlImpl();
		jdbc.setDataSource(dataSource);
		return jdbc;
	}

}
