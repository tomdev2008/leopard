package io.leopard.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

public class LeopardDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		String mimeType = getServletContext().getMimeType(uri);

		System.err.println("request:" + uri + " mimeType:" + mimeType);
		super.noHandlerFound(request, response);
	}

	// @Override
	// public void init(ServletConfig config) throws ServletException {
	// System.out.println("LeopardDispatcherServlet init:" + config);
	// super.init(config);
	// }

}
