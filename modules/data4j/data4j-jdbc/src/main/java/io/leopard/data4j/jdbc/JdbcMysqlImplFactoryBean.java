package io.leopard.data4j.jdbc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

public class JdbcMysqlImplFactoryBean extends JdbcMysqlImpl {
	private String host;
	private String database;
	private String user;
	private String password;

	private int maxPoolSize = 15;
	private int port = 3306;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
//		System.out.println("host:" + host + " database:" + this.database + " maxPoolSize:" + maxPoolSize);
		{
			DataSource dataSource = super.getDataSource();
			if (dataSource != null) {
				return;
			}
		}
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setDatabase(database);
		dataSource.setHost(host);
		dataSource.setPort(port);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.init();
		super.setDataSource(dataSource);
	}

	@PreDestroy
	public void destroy() {
		DataSource dataSource = super.getDataSource();
		if (dataSource != null && dataSource instanceof JdbcDataSource) {
			((JdbcDataSource) dataSource).destroy();
		}
	}

}
