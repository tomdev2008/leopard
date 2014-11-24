package io.leopard.web.interceptor;

import io.leopard.commons.utility.NumberUtil;
import io.leopard.commons.utility.SystemUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 页面延迟响应.
 * 
 * @author 阿海
 * 
 */
@Component
public class PageDelayInterceptor implements IInterceptor {

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

	private static IInterceptor customInterceptor = null;

	public static void setCustomInterceptor(IInterceptor customInterceptor) {
		PageDelayInterceptor.customInterceptor = customInterceptor;
	}

	@Override
	public void preHandle(String uri, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (customInterceptor != null) {
			customInterceptor.preHandle(uri, request, response, handler);
			return;
		}
		if (!delayOn) {
			return;
		}
		if (isIgnoreUri(uri)) {
			return;
		}
		int mills = NumberUtil.random(5000);
		if (mills > 20) {
			SystemUtil.sleep(mills);
		}
	}

}
