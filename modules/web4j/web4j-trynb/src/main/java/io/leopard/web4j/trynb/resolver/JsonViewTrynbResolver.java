package io.leopard.web4j.trynb.resolver;

import javax.servlet.http.HttpServletRequest;

import io.leopard.core.exception.StatusCodeException;
import io.leopard.web4j.trynb.model.TrynbInfo;
import io.leopard.web4j.view.JsonView;

import org.springframework.web.servlet.ModelAndView;

public class JsonViewTrynbResolver implements TrynbResolver {

	@Override
	public ModelAndView resolveView(HttpServletRequest request, String uri, Exception exception, TrynbInfo trynbInfo, Class<?> returnType) {
		if (!JsonView.class.isAssignableFrom(returnType)) {
			return null;
		}

		JsonView jsonView = new JsonView();
		if (exception instanceof StatusCodeException) {
			StatusCodeException e = (StatusCodeException) exception;
			jsonView.setStatus(e.getStatus());
			jsonView.setMessage(e.getMessage());
		}
		else {
			jsonView.setStatus(trynbInfo.getStatusCode());
			jsonView.setMessage(trynbInfo.getMessage());
		}
		return jsonView;
	}

}
