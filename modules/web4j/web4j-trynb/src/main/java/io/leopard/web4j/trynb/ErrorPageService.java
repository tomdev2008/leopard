package io.leopard.web4j.trynb;

import io.leopard.web4j.trynb.model.ErrorConfig;
import io.leopard.web4j.trynb.model.ErrorPage;

import javax.servlet.http.HttpServletRequest;

public interface ErrorPageService {

	ErrorConfig findErrorInfo(String url);

	ErrorPage parseErrorPage(HttpServletRequest request, String uri, Exception exception);

}
