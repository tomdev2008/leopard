package io.leopard.data4j.jdbc.builder;

import io.leopard.data4j.jdbc.StatementParameter;

public interface SqlBuilder {

	/**
	 * 获取SQL语句.
	 * 
	 * @return
	 */
	String getSql();

	/**
	 * 获取参数
	 * 
	 * @return
	 */
	StatementParameter getParam();
}
