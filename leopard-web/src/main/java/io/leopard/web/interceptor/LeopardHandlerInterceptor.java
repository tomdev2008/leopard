package io.leopard.web.interceptor;

import io.leopard.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LeopardHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	private PageDelayInterceptor pageDelayInterceptor;

	@Autowired
	private IInterceptor csrfInterceptor;
	@Autowired
	private IInterceptor monitorPermissionInterceptor;

	@Autowired
	private IInterceptor webservicePermissionInterceptor;
	@Autowired
	private IInterceptor connectionLimitInterceptor;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// System.err.println("csrfInterceptor:" + csrfInterceptor);
		// System.err.println("monitorPermissionInterceptor:" + monitorPermissionInterceptor);
		// System.err.println("webservicePermissionInterceptor:" + webservicePermissionInterceptor);
		String requestUri = RequestUtil.getRequestContextUri(request);

		pageDelayInterceptor.preHandle(requestUri, request, response, handler);

		connectionLimitInterceptor.preHandle(requestUri, request, response, handler);

		csrfInterceptor.preHandle(requestUri, request, response, handler);
		monitorPermissionInterceptor.preHandle(requestUri, request, response, handler);
		webservicePermissionInterceptor.preHandle(requestUri, request, response, handler);
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
