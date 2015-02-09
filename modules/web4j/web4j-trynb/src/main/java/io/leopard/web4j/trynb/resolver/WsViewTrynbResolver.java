package io.leopard.web4j.trynb.resolver;

import io.leopard.core.exception.StatusCodeException;
import io.leopard.web4j.trynb.model.ErrorPage;
import io.leopard.web4j.view.WsView;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public class WsViewTrynbResolver implements TrynbResolver {

	@Override
	public ModelAndView resolveView(HttpServletRequest request, String uri, Exception exception, ErrorPage errorPage, Class<?> returnType) {
		if (!returnType.equals(WsView.class)) {
			return null;
		}
		WsView webserviceView = new WsView(null);
		if (exception instanceof StatusCodeException) {
			StatusCodeException e = (StatusCodeException) exception;
			webserviceView.setStatus(e.getStatus());
			webserviceView.setMessage(e.getMessage());
		}
		else {
			webserviceView.setStatus(errorPage.getStatusCode());
			webserviceView.setMessage(errorPage.getMessage());
		}
		return webserviceView;
	}

}
