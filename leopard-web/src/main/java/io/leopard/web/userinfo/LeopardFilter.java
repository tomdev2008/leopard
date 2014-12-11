package io.leopard.web.userinfo;

import io.leopard.web4j.session.SessionService;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LeopardFilter implements Filter {
	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	protected SessionService sessionService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		HttpServletResponse response = (HttpServletResponse) res;
		LeopardRequestWrapper httpRequestWraper = new LeopardRequestWrapper(request, response, sessionService);
		chain.doFilter(httpRequestWraper, response);
	}

	@Override
	public void destroy() {
		logger.info("destroy");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init");
	}

}
