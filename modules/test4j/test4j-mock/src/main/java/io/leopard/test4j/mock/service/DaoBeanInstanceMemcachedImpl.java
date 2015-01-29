package io.leopard.test4j.mock.service;

public class DaoBeanInstanceMemcachedImpl extends AbstractDaoBeanInstance {

	@Override
	protected String getImplClassName(String className) {
		String implClassName = className.replace("dao.", "dao.memcached.");
		implClassName += "MemcachedImpl";
		return implClassName;
	}

}
