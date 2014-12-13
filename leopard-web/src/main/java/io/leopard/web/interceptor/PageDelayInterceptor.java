package io.leopard.web.interceptor;

import io.leopard.commons.utility.NumberUtil;
import io.leopard.commons.utility.SystemUtil;
import io.leopard.web4j.servlet.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面延迟响应.
 * 
 * @author 阿海
 * 
 */
@Component
public class PageDelayInterceptor implements HandlerInterceptor {

	private boolean delayOn = false;// 是否开启接口访问随机延迟响应.

	public boolean isDelayOn() {
		return delayOn;
	}

	public void setDelayOn(boolean delayOn) {
		this.delayOn = delayOn;
	}

	public static boolean isIgnoreUri(String uri) {
		if (uri.startsWith("/leopard/")) {
			return true;
		}
		if (uri.startsWith("/admin/")) {
			return true;
		}
		if (uri.startsWith("/monitor/")) {
			return true;
		}
		return false;
	}

	// private static IInterceptor customInterceptor = null;
	//
	// public static void setCustomInterceptor(IInterceptor customInterceptor) {
	// PageDelayInterceptor.customInterceptor = customInterceptor;
	// }

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = RequestUtil.getRequestContextUri(request);
		// if (customInterceptor != null) {
		// customInterceptor.preHandle(requestUri, request, response, handler);
		// return true;
		// }
		if (!delayOn) {
			return true;
		}
		if (isIgnoreUri(requestUri)) {
			return true;
		}
		int mills = NumberUtil.random(5000);
		if (mills > 20) {
			SystemUtil.sleep(mills);
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
