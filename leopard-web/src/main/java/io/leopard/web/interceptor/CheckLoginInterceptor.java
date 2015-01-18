package io.leopard.web.interceptor;

import io.leopard.web.userinfo.UserinfoService;
import io.leopard.web4j.nobug.csrf.CsrfUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 检查是否已登录.
 * 
 * @author 阿海
 * 
 */
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserinfoService userinfoService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean isExcludeUri = userinfoService.isExcludeUri(request);
		CsrfUtil.setExcludeUri(request, isExcludeUri);
		if (isExcludeUri) {
			return true;
		}
		Object account = userinfoService.login(request, response);
		if (account == null) {
			this.userinfoService.showLoginBox(request, response);
			return false;
		}
		ConnectionLimitInterceptor.setAccount(request, account);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
