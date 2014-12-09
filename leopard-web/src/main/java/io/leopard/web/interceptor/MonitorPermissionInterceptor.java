package io.leopard.web.interceptor;

import io.leopard.web.mvc.util.RequestUtil;
import io.leopard.web4j.permission.PermissionService;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitorPermissionInterceptor implements IInterceptor {

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
	public void preHandle(String requestUri, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// String requestUri = RequestUtil.getRequestContextUri(request);
		if (!requestUri.startsWith("/monitor/")) {
			return;
		}
		if (MONITOR_IGNORE_SET.contains(requestUri)) {
			return;
		}
		// proxyIp = "127.0.0.2";
		if (permissionService != null) {
			String proxyIp = RequestUtil.getProxyIp(request);
			permissionService.checkMonitorServer(proxyIp);
		}
	}

}
