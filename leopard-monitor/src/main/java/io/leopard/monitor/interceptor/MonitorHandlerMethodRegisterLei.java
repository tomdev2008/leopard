package io.leopard.monitor.interceptor;

import io.leopard.monitor.url.UrlInfoService;
import io.leopard.topnb.PerformanceStackTraceService;
import io.leopard.web4j.method.HandlerMethodRegisterLei;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

@Component
public class MonitorHandlerMethodRegisterLei implements HandlerMethodRegisterLei {

	@Override
	public void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping, String uri) {
		if (this.hasMonitor(mapping, method, uri)) {
			PerformanceStackTraceService.add(uri);
		}
		UrlInfoService.add(uri);
	}

	/**
	 * 判断接口是否需要监控.
	 * 
	 * @param info
	 * @param method
	 * @return
	 */
	protected boolean hasMonitor(RequestMappingInfo info, Method method, String uri) {
		String className = method.getDeclaringClass().getName();
		if (className.startsWith("io.leopard")) {
			return false;
		}
		// String uri =
		// info.getPatternsCondition().getPatterns().iterator().next();
		if (uri.startsWith("/admin/")) {
			return false;
		}
		if (uri.startsWith("/monitor/")) {
			return false;
		}
		return true;
	}
}
