package io.leopard.web4j.trynb;

public interface ErrorPageDao {

	boolean add(ErrorConfig errorConfig);

	ErrorConfig findErrorInfo(String url);

}
