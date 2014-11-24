package io.leopard.web.userinfo.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	/**
	 * 重定向到指定的URL.
	 * 
	 * @param url
	 * @param response
	 * @return
	 */
	public static void sendRedirect(String url, HttpServletResponse response) {
		try {
			response.sendRedirect(url);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
