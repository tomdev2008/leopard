package io.leopard.web.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 性能测试用的servlet.
 * 
 * @author 阿海
 * 
 */
public class BenchmarkServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// String clientIPAddress = req.getRemoteAddr();
		res.setContentType("text/html");
		ServletOutputStream out = res.getOutputStream();
		out.println("ok");
		out.close();
	}
}
