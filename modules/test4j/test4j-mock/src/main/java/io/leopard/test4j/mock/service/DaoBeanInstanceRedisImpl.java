package io.leopard.test4j.mock.service;


public class DaoBeanInstanceRedisImpl extends AbstractDaoBeanInstance {

	@Override
	protected String getImplClassName(String className) {
		String implClassName = className.replace("dao.", "dao.redis.");
		implClassName += "RedisImpl";
		return implClassName;
	}

}
