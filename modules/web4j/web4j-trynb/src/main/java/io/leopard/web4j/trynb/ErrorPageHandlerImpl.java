package io.leopard.web4j.trynb;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.core.exception.StatusCodeException;
import io.leopard.core.exception.other.OutSideException;
import io.leopard.web4j.view.JsonView;
import io.leopard.web4j.view.OkTextView;
import io.leopard.web4j.view.WsView;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class ErrorPageHandlerImpl extends ContextImpl implements ErrorPageHandler {

	private static ErrorPageService errorPageService;

	public static void setErrorPageService(ErrorPageService errorPageService) {
		ErrorPageHandlerImpl.errorPageService = errorPageService;
	}

	@Override
	public WsView toWsView(HttpServletRequest request, String uri, Exception exception) {
		WsView webserviceView = new WsView(null);
		if (exception instanceof StatusCodeException) {
			StatusCodeException e = (StatusCodeException) exception;
			webserviceView.setStatus(e.getStatus());
			webserviceView.setMessage(e.getMessage());
		}
		else {
			ErrorPage errorPage = errorPageService.parseErrorPage(request, uri, exception);
			webserviceView.setStatus(errorPage.getStatusCode());
			webserviceView.setMessage(errorPage.getMessage());
		}
		return webserviceView;
	}

	@Override
	public JsonView toJsonView(HttpServletRequest request, String uri, Exception exception) {
		JsonView jsonView = new JsonView();
		if (exception instanceof StatusCodeException) {
			StatusCodeException e = (StatusCodeException) exception;
			jsonView.setStatus(e.getStatus());
			jsonView.setMessage(e.getMessage());
		}
		else {
			ErrorPage errorPage = errorPageService.parseErrorPage(request, uri, exception);
			jsonView.setStatus(errorPage.getStatusCode());
			jsonView.setMessage(errorPage.getMessage());
		}
		return jsonView;
	}

	/**
	 * 成功文本.
	 * 
	 * @param request
	 * @param exception
	 * @return
	 */
	@Override
	public OkTextView toOkTextView(HttpServletRequest request, String uri, Exception exception) {
		String message;
		if (exception instanceof OutSideException) {
			// 外部接口出错
			message = "560:" + exception.getMessage();
		}
		else {
			message = exception.getMessage();
			if (StringUtils.isEmpty(message)) {
				message = exception.toString();
			}
		}
		logger.error(exception.getMessage(), exception);
		return new OkTextView(message);
	}

	@Override
	public ErrorPage parseErrorPage(HttpServletRequest request, String uri, Exception exception) {
		return errorPageService.parseErrorPage(request, uri, exception);
	}

	@Override
	public ErrorConfig findErrorInfo(String url) {
		return errorPageService.findErrorInfo(url);
	}
}
