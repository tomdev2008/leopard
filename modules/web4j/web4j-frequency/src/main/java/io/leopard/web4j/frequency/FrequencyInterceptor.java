package io.leopard.web4j.frequency;

import io.leopard.data4j.redis.Redis;
import io.leopard.web4j.servlet.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 访问频率限制过滤器.
 * 
 * @author 阿海
 * 
 */
public class FrequencyInterceptor implements HandlerInterceptor, BeanFactoryAware {
	@Autowired
	private FrequencyLei frequencyLei;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory context = (DefaultListableBeanFactory) beanFactory;
		Map<String, Redis> map = context.getBeansOfType(Redis.class);
		if (!map.isEmpty()) {
			Redis redis = map.get("sessionRedis");
			if (redis == null) {
				redis = map.entrySet().iterator().next().getValue();// 获取第一个
			}
			((FrequencyLeiImpl) frequencyLei).setRedis(redis);
		}
	}

	private Map<Integer, Integer> data = new HashMap<Integer, Integer>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		int hashCode = handler.hashCode();
		Integer seconds = data.get(hashCode);
		if (seconds == null) {
			return true;
		}
		// TODO ahai 根据Controller方法中使用的session参数进行判断?
		Object account = request.getAttribute("account");
		if (account == null) {
			return true;
		}
		String requestUri = RequestUtil.getRequestContextUri(request);
		frequencyLei.request(account.toString(), requestUri, seconds);
		return true;
	}

	public static void setAccount(HttpServletRequest request, Object account) {
		request.setAttribute("account", account);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
