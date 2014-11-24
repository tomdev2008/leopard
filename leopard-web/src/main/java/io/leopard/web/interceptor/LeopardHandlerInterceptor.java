package io.leopard.web.interceptor;

import io.leopard.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LeopardHandlerInterceptor implements HandlerInterceptor {

	@Autowired(required = false)
	private PageDelayInterceptor pageDelayInterceptor;

	@Autowired(required = false)
	private IInterceptor csrfInterceptor;
	@Autowired(required = false)
	private IInterceptor monitorPermissionInterceptor;

	@Autowired(required = false)
	private IInterceptor webservicePermissionInterceptor;
	@Autowired(required = false)
	private IInterceptor connectionLimitInterceptor;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = RequestUtil.getRequestContextUri(request);

		if (pageDelayInterceptor != null) {
			pageDelayInterceptor.preHandle(requestUri, request, response, handler);
		}
		if (connectionLimitInterceptor != null) {
			connectionLimitInterceptor.preHandle(requestUri, request, response, handler);
		}
		if (csrfInterceptor != null) {
			csrfInterceptor.preHandle(requestUri, request, response, handler);
		}
		if (monitorPermissionInterceptor != null) {
			monitorPermissionInterceptor.preHandle(requestUri, request, response, handler);
		}
		if (webservicePermissionInterceptor != null) {
			webservicePermissionInterceptor.preHandle(requestUri, request, response, handler);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// System.err.println("postHandle:" + request.getRequestURI());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// System.err.println("afterCompletion:" + request.getRequestURI());
	}
}
