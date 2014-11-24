package io.leopard.web.security;

import javax.servlet.http.HttpServletRequest;

public class CsrfUtil {

	public static void setExcludeUri(HttpServletRequest request, boolean isExcludeUri) {
		request.setAttribute("isExcludeUri", isExcludeUri);
	}

	public static boolean isExcludeUri(HttpServletRequest request) {
		Object obj = request.getAttribute("isExcludeUri");
		if (obj == null) {
			return false;
		}
		return (Boolean) obj;
	}
}
