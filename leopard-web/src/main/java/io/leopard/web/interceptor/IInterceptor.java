package io.leopard.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web拦截器.
 * 
 * @author 阿海
 * 
 */
public interface IInterceptor {

	void preHandle(String requestUri, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

}
