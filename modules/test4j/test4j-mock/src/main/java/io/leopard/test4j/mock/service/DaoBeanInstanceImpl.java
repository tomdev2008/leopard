package io.leopard.test4j.mock.service;

public class DaoBeanInstanceImpl extends AbstractDaoBeanInstance {

	@Override
	protected String getImplClassName(String className) {
		String implClassName = className.replace("dao.", "dao.impl.");
		implClassName += "Impl";
		return implClassName;
	}

}
