package io.leopard.web4j.trynb;

import javax.servlet.http.HttpServletRequest;

public interface ErrorPageService {
	boolean add(ErrorConfig errorConfig);

	ErrorConfig findErrorInfo(String url);

	ErrorPage parseErrorPage(HttpServletRequest request, String uri, Exception exception);

}
