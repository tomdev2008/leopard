package io.leopard.web.interceptor;

import io.leopard.commons.utility.ListUtil;
import io.leopard.ext.connectionlimit.ConnectionLimitDao;
import io.leopard.web.userinfo.UriListChecker;
import io.leopard.web.userinfo.service.ConfigHandler;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 访问频率限制过滤器.
 * 
 * @author 阿海
 * 
 */
@Component
public class ConnectionLimitInterceptor implements IInterceptor {

	@Autowired(required = false)
	private ConnectionLimitDao connectionLimitDao;
	@Resource
	private ConfigHandler loginHandler;

	private UriListChecker includeConnectionLimitUriListChecker;// 并发限制的URL列表.

	@PostConstruct
	public void init() {
		List<String> connectionLimitUris = loginHandler.getConnectionLimitIncludeUris();
		connectionLimitUris = ListUtil.defaultList(connectionLimitUris);
		// System.err.println("connectionLimitUris:" + connectionLimitUris);

		includeConnectionLimitUriListChecker = new UriListChecker(connectionLimitUris);
	}

	@Override
	public void preHandle(String requestUri, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (connectionLimitDao == null) {
			return;
		}
		boolean exists = includeConnectionLimitUriListChecker.exists(requestUri);
		// System.err.println("exists:" + exists + " uri:" + uri);
		if (!exists) {
			return;
		}
		Object account = request.getAttribute("account");
		if (account == null) {
			return;
		}
		connectionLimitDao.request(account.toString(), requestUri);
	}

	public static void setAccount(HttpServletRequest request, Object account) {
		request.setAttribute("account", account);
	}

}
