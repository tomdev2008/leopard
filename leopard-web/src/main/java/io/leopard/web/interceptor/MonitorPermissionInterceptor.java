package io.leopard.web.interceptor;

import io.leopard.web4j.permission.PermissionService;
import io.leopard.web4j.view.RequestUtil;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MonitorPermissionInterceptor implements HandlerInterceptor {

	@Autowired(required = false)
	private PermissionService permissionService;

	protected static Set<String> MONITOR_IGNORE_SET = new HashSet<String>();
	static {
		MONITOR_IGNORE_SET.add("/monitor/performance.do");
		MONITOR_IGNORE_SET.add("/monitor/connection.do");
		MONITOR_IGNORE_SET.add("/monitor/thread.do");
		MONITOR_IGNORE_SET.add("/monitor/api.do");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = RequestUtil.getRequestContextUri(request);
		if (!requestUri.startsWith("/monitor/")) {
			return true;
		}
		if (MONITOR_IGNORE_SET.contains(requestUri)) {
			return true;
		}
		// proxyIp = "127.0.0.2";
		if (permissionService != null) {
			String proxyIp = RequestUtil.getProxyIp(request);
			permissionService.checkMonitorServer(proxyIp);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

}
