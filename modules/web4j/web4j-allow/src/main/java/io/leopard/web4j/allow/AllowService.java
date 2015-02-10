package io.leopard.web4j.allow;

import org.springframework.dao.PermissionDeniedDataAccessException;

public interface AllowService {

	/**
	 * 根据uri，ip判断是否有权限.
	 * 
	 * @param requestUri
	 * @param proxyIp
	 * @return
	 */
	boolean isAllow(String requestUri, String proxyIp);

	/**
	 * 跟uri，ip检查是否有权限.
	 * 
	 * @param requestUri
	 * @param proxyIp
	 * @throws PermissionDeniedDataAccessException
	 */
	void checkAllow(String requestUri, String proxyIp) throws PermissionDeniedDataAccessException;
}
