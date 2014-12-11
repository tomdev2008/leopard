package io.leopard.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LeopardHandlerInterceptor implements HandlerInterceptor {

	@Autowired(required = false)
	private PageDelayInterceptor pageDelayInterceptor;

	@Autowired(required = false)
	private CsrfInterceptor csrfInterceptor;

	@Autowired(required = false)
	private MonitorPermissionInterceptor monitorPermissionInterceptor;

	@Autowired(required = false)
	private WebservicePermissionInterceptor webservicePermissionInterceptor;
	@Autowired(required = false)
	private ConnectionLimitInterceptor connectionLimitInterceptor;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// System.err.println("pageDelayInterceptor:" + pageDelayInterceptor);
		if (pageDelayInterceptor != null) {
			pageDelayInterceptor.preHandle(request, response, handler);
		}
		if (connectionLimitInterceptor != null) {
			connectionLimitInterceptor.preHandle(request, response, handler);
		}
		if (csrfInterceptor != null) {
			csrfInterceptor.preHandle(request, response, handler);
		}
		if (monitorPermissionInterceptor != null) {
			monitorPermissionInterceptor.preHandle(request, response, handler);
		}
		if (webservicePermissionInterceptor != null) {
			webservicePermissionInterceptor.preHandle(request, response, handler);
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
