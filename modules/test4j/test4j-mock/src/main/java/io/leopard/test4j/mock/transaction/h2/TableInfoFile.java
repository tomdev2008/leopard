package io.leopard.test4j.mock.transaction.h2;

/**
 * 表结构.
 * 
 * @author ahai
 * 
 */
public interface TableInfoFile {

	boolean hasData();

	String getDatabaseName();

	String getSqlContent();

	String getAlterSqlContent();
}
