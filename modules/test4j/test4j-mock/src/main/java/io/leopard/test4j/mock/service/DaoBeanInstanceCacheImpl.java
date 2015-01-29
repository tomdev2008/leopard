package io.leopard.test4j.mock.service;

public class DaoBeanInstanceCacheImpl extends AbstractDaoBeanInstance {

	@Override
	protected String getImplClassName(String className) {
		String implClassName = className.replace("dao.", "dao.cache.");
		implClassName += "CacheImpl";
		return implClassName;
	}

}
