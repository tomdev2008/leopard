package io.leopard.monitor.interceptor;

import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.service.PerformanceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 请求耗时.
 * 
 * @author 阿海
 * 
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
	

	private static final PerformanceService performanceService = TopnbBeanFactory.getPerformanceService();

	private static final ThreadLocal<Long> TIME = new ThreadLocal<Long>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long time = System.nanoTime();
		TIME.set(time);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		long startTime = TIME.get();
		long endTime = System.nanoTime();
		long time = (endTime - startTime) / 1000L; // time 单位:微妙
		String methodName = "io.leopard.web.interceptor.TimeInterceptor.doHandle";
		performanceService.add(methodName, time);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
