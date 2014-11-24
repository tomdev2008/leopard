package io.leopard.web.userinfo;

import io.leopard.web4j.passport.SessionUtil;
import io.leopard.web4j.servlet.CookieUtil;
import io.leopard.web4j.session.HttpSessionWrapper;
import io.leopard.web4j.session.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class SessionRequestWrapper extends HttpServletRequestWrapper {
	private final String sid;
	private HttpSession session;
	private static int expiry = 86400;// 1天

	public static void setExpiry(int expiry) {
		System.out.println("session expiry:" + expiry);
		if (expiry < 100) {
			throw new IllegalArgumentException("session超时时间不能小于100[" + expiry + "].");
		}
		SessionRequestWrapper.expiry = expiry;
	}

	// protected HttpServletRequest request;
	protected HttpServletResponse response;

	private SessionService sessionService;

	public SessionRequestWrapper(HttpServletRequest request, HttpServletResponse response, SessionService sessionService) {
		super(request);
		this.sessionService = sessionService;
		this.response = response;
		this.sid = this.createJsessionIdCookie();
	}

	private String createJsessionIdCookie() {
		String sid = CookieUtil.getCookie(SessionUtil.SESSIONID_COOKIE_NAME, this);
		if (StringUtils.isEmpty(sid)) {
			// 写cookie
			sid = java.util.UUID.randomUUID().toString();
			CookieUtil.setCookie(SessionUtil.SESSIONID_COOKIE_NAME, sid, this, response);// http
																							// only
			// this.addCookie(sid);
			this.setAttribute(SessionUtil.SESSIONID_COOKIE_NAME, sid);
		}
		return sid;
	}

	@Override
	public HttpSession getSession(boolean create) {

		if (session != null) {
			// System.out.println("getSession:" + session);// + " uri:" +
			// this.getHttpServletRequest().getRequestURI());
			return session;
		}
		if (create) {
			// super.getSession(create)
			this.session = new HttpSessionWrapper(this.sid, expiry, sessionService);
			return session;
		}
		else {
			return null;
		}
	}

	@Override
	public HttpSession getSession() {
		return this.getSession(true);
	}
}
