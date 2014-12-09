package io.leopard.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MonitorContext {

	private static ThreadLocal<HttpServletRequest> requestContext = new ThreadLocal<HttpServletRequest>();

	protected static final Log logger = LogFactory.getLog(MonitorContext.class);

	public static void setRequest(HttpServletRequest request) {
		requestContext.set(request);
	}

	public static HttpServletRequest getRequest() {
		return requestContext.get();
	}

	// public static String getProxyIp() {
	// HttpServletRequest request = getRequest();
	// if (request == null) {
	// return null;
	// }
	// return RequestUtil.getProxyIp(request);
	// }
	//
	// public static String getRequestUri() {
	// HttpServletRequest request = getRequest();
	// if (request == null) {
	// return null;
	// }
	// String uri = RequestUtil.getRequestContextUri(request);
	// return uri;
	// }

}
