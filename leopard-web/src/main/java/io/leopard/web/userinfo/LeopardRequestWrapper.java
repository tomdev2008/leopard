package io.leopard.web.userinfo;

import io.leopard.burrow.lang.Json;
import io.leopard.web.mvc.util.RequestUtil;
import io.leopard.web.security.xss.XssAttributeCheckUtil;
import io.leopard.web.security.xss.XssAttributeData;
import io.leopard.web.security.xss.XssException;
import io.leopard.web.security.xss.XssUtil;
import io.leopard.web4j.parameter.PageParameterUtil;
import io.leopard.web4j.session.SessionService;
import io.leopard.web4j.validator.ParameterValidator;
import io.leopard.web4j.validator.ParameterValidatorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户信息参数封装
 * 
 * sessUsername和proxyIp
 * 
 * @author 阿海
 * 
 */
public class LeopardRequestWrapper extends HttpServletRequestWrapper {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected boolean hasSessionRequestWrapper;

	public LeopardRequestWrapper(HttpServletRequest request, HttpServletResponse response, SessionService sessionService) {
		super(request);
		this.response = response;
		if (sessionService == null) {
			this.request = request;
			hasSessionRequestWrapper = false;
		}
		else {
			this.request = new SessionRequestWrapper(request, response, sessionService);
			hasSessionRequestWrapper = true;
		}
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (hasSessionRequestWrapper) {
			return this.request.getSession(create);
		}
		else {
			return super.getSession(create);
		}
	}

	@Override
	public HttpSession getSession() {
		if (hasSessionRequestWrapper) {
			return this.request.getSession();
		}
		else {
			return super.getSession();
		}
	}

	public String getSuperParameter(String name) {
		return super.getParameter(name);
	}

	@Override
	public String getParameter(String name) {
		String[] values = this.getParameterValues(name);
		if (values == null) {
			return null;
		}
		else {
			return values[0];
		}
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values;
		if (PageParameterUtil.isSpecialName(name)) {
			String value = PageParameterUtil.getParameterValues(request, response, name);
			values = new String[] { value };
		}
		else {
			// 当前不少特殊的参数名称，直接返回
			values = super.getParameterValues(name);
			if (values != null) {
				this.checkXxxParameter(name, values);
			}
		}

		// if (values != null) {
		ParameterValidator validator = ParameterValidatorUtil.getValidator(name);
		if (validator != null) {
			if (values == null) {
				validator.check(null);
			}
			else {
				validator.check(values[0]);
			}
		}
		// }
		return values;
	}

	protected void checkXxxParameter(String name, String[] values) {
		if (!XssUtil.isEnable()) {
			return;
		}

		for (String value : values) {
			try {
				XssUtil.checkParameter(name, value);
			}
			catch (XssException e) {
				String uri = RequestUtil.getRequestContextUri(request);
				boolean isNoXss = XssAttributeData.isNoXss(uri, name);
				if (!isNoXss) {
					throw e;
				}
			}
		}

	}

	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(name);
		boolean needCheckXss = needCheckXss();
		if (!needCheckXss) {
			return value;
		}
		try {
			XssAttributeCheckUtil.checkAttribute(name, value);
			return value;
		}
		catch (XssException e) {
			System.err.println("attribute name:" + name + " " + e.getMessage() + " json:" + Json.toFormatJson(value));
			throw e;
		}
	}

	protected boolean needCheckXss() {
		// TODO ahai 这里能否做缓存?
		Boolean ignore = (Boolean) super.getAttribute(XssAttributeCheckUtil.IGNORE_XSS_ATTRIBUTE_NAME);
		if (ignore != null && ignore) {
			return false;
		}
		return true;
	}

}
