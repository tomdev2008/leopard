package io.leopard.web.interceptor;

import io.leopard.web.security.CsrfService;
import io.leopard.web.security.DomainWhiteListConfig;
import io.leopard.web.security.RefererSecurityValidator;
import io.leopard.web.security.xss.XssUtil;
import io.leopard.web.userinfo.service.ConfigHandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class CsrfInterceptor implements IInterceptor {

	@Autowired
	private CsrfService csrfService;

	@Resource
	private ConfigHandler loginHandler;

	@Autowired(required = false)
	protected DomainWhiteListConfig domainWhiteListConfig;

	@PostConstruct
	public void init() {
		if (domainWhiteListConfig == null) {
			RefererSecurityValidator.setDoaminWhiteList(loginHandler.getRefererDomainWhiteSet());
		}
		else {
			RefererSecurityValidator.setDoaminWhiteList(domainWhiteListConfig.getRefererDomainWhiteSet());
		}
	}

	@Override
	public void preHandle(String uri, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		XssUtil.initXSS(request, handler);

		if (csrfService.isEnable()) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			csrfService.check(handlerMethod, request, response);
		}

	}

}
