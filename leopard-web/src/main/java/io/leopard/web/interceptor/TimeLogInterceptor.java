package io.leopard.web.interceptor;

import io.leopard.web.userinfo.ConfigHandler;
import io.leopard.web4j.servlet.RequestUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面耗时日志.
 * 
 * @author 阿海
 * 
 */
@Component
public class TimeLogInterceptor implements HandlerInterceptor {

	@Resource
	private ConfigHandler loginHandler;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = RequestUtil.getRequestContextUri(request);

		// System.err.println("TimeLogInterceptor preHandle:" + requestUri);

		if (!loginHandler.isEnableTimeLog()) {// 是否开启页面耗时日志,日志写在time.log
			return true;
		}
		String queryString = request.getQueryString();
		String url;
		if (StringUtils.isNotEmpty(queryString)) {
			url = requestUri + "?" + queryString;
		}
		else {
			url = requestUri;
		}
		// FIXME ahai 页面耗时日志未实现
		// AvgTime avgTime = AvgTime.start(uri);// 平均耗时统计
		// avgTime.end();

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
