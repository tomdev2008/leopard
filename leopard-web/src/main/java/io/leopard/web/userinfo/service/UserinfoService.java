package io.leopard.web.userinfo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserinfoService {
	// /**
	// * 是否开启耗时日志.
	// *
	// * @return
	// */
	// boolean isEnableTimeLog();

	boolean isExcludeUri(HttpServletRequest request);

	Long login(HttpServletRequest request, HttpServletResponse response);

	void showLoginBox(HttpServletRequest request, HttpServletResponse response);

}
