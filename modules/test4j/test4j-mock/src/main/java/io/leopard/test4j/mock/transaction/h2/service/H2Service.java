package io.leopard.test4j.mock.transaction.h2.service;

public interface H2Service {

	void importDatabase();

	void createTable(String filename);

	void importMemcacheTable();

	// boolean existDatabase();

	void createH2TableInfoTable(int hashCode);

}
