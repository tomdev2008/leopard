package io.leopard.web4j.trynb;

import io.leopard.web4j.view.JsonView;
import io.leopard.web4j.view.OkTextView;
import io.leopard.web4j.view.WsView;

import javax.servlet.http.HttpServletRequest;

public interface ErrorPageHandler {

	OkTextView toOkTextView(HttpServletRequest request, String uri, Exception exception);

	JsonView toJsonView(HttpServletRequest request, String uri, Exception exception);

	WsView toWsView(HttpServletRequest request, String uri, Exception exception);

	ErrorPage parseErrorPage(HttpServletRequest request, String uri, Exception exception);

	ErrorConfig findErrorInfo(String url);

}
