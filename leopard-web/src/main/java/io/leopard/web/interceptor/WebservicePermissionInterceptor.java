package io.leopard.web.interceptor;

import io.leopard.web4j.permission.PermissionService;
import io.leopard.web4j.permission.config.Permission;
import io.leopard.web4j.view.RequestUtil;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class WebservicePermissionInterceptor implements IInterceptor {

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
		// System.err.println("preHandle:" + requestUri);
		if (!requestUri.startsWith("/webservice/")) {
			return;
		}
		// TODO ahai 这里要判断类型？
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		boolean hasPermission = this.hasPermission(handlerMethod);
		if (!hasPermission) {
			// 不检查权限.
			return;
		}

		// proxyIp = "192.168.1.1";
		if (permissionService != null) {
			String proxyIp = RequestUtil.getProxyIp(request);
			permissionService.checkPermission(requestUri, proxyIp);
		}
	}

	protected boolean hasPermission(HandlerMethod handlerMethod) {

		Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
		if (permission != null && permission.enable() == false) {
			// 不检查权限.
			return false;
		}
		return true;
	}

}
