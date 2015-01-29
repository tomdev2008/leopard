package io.leopard.test4j.mock.transaction.h2.service;

public interface H2Dao {

	boolean isH2DataSource();

	// boolean existDatabase();

	void importMemcacheTable();

	// String getFullPath();

	void importDatabaseByContent(String content);

	boolean existTable(String table);

	int getH2TableInfoHashCode();

	String getSqlContent();

	void createH2TableInfoTable(int hashCode);

	String getAlterSqlContent();

}
