package io.leopard.web.interceptor;

import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.service.PerformanceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LeopardHandlerInterceptor implements HandlerInterceptor {

	private static final PerformanceService performanceService = TopnbBeanFactory.getPerformanceService();

	private static final ThreadLocal<Long> TIME = new ThreadLocal<Long>();

	@Autowired(required = false)
	private ProxyInterceptor proxyInterceptor;

	@Autowired(required = false)
	private SkipFilterInterceptor skipFilterInterceptor;

	@Autowired(required = false)
	private TimeLogInterceptor timeLogInterceptor;

	@Autowired(required = false)
	private CheckLoginInterceptor checkLoginInterceptor;

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
		long time = System.nanoTime();
		TIME.set(time);

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
		long startTime = TIME.get();
		long endTime = System.nanoTime();
		long time = (endTime - startTime) / 1000L; // time 单位:微妙
		String methodName = "io.leopard.web.interceptor.LeopardHandlerInterceptor.doHandle";
		performanceService.add(methodName, time);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// System.err.println("afterCompletion:" + request.getRequestURI());
	}
}
