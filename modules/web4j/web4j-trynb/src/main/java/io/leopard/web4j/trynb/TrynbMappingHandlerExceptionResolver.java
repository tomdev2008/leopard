package io.leopard.web4j.trynb;

import io.leopard.web4j.view.JsonView;
import io.leopard.web4j.view.OkTextView;
import io.leopard.web4j.view.WsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class TrynbMappingHandlerExceptionResolver implements HandlerExceptionResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ErrorPageHandlerImpl errorPageHandler;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		String uri = request.getRequestURI();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Class<?> returnType = handlerMethod.getMethod().getReturnType();
		// System.err.println("returnType:" + returnType);
		if (JsonView.class.isAssignableFrom(returnType)) {
			return errorPageHandler.toJsonView(request, uri, exception);
		}
		else if (returnType.equals(WsView.class)) {
			return errorPageHandler.toWsView(request, uri, exception);
		}
		else if (returnType.equals(OkTextView.class)) {
			return errorPageHandler.toOkTextView(request, uri, exception);
		}
		else {
			return null;
		}
	}

}
