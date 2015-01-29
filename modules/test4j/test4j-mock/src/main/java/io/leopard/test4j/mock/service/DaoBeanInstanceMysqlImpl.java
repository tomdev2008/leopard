package io.leopard.test4j.mock.service;

public class DaoBeanInstanceMysqlImpl extends AbstractDaoBeanInstance {

	@Override
	protected String getImplClassName(String className) {
		String implClassName = className.replace("dao.", "dao.mysql.");
		implClassName += "MysqlImpl";
		return implClassName;
	}

}
