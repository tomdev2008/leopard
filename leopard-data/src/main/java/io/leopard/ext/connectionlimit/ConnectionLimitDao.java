package io.leopard.ext.connectionlimit;

import io.leopard.core.exception.ConnectionLimitException;

/**
 * 并发(连接数)限制，相同URI一个用户3秒只能访问1次.
 * 
 * @author 阿海
 * 
 */
public interface ConnectionLimitDao {

	void request(String user, String uri) throws ConnectionLimitException;

}
