package io.leopard.data4j.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class SpringJedisConnectionFactory extends JedisConnectionFactory {

	private String server;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public void afterPropertiesSet() {
		if (StringUtils.isEmpty(server) || server.indexOf("null") > -1) {
			throw new NullPointerException("redis server属性未设置.");
		}
		String[] list = server.split(":");
		String hostName = list[0];
		int port = Integer.parseInt(list[1]);
		super.setHostName(hostName);
		super.setPort(port);
		super.afterPropertiesSet();
	}
}
