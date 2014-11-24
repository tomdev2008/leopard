package io.leopard.web.mvc.controller;

import io.leopard.test.mock.Mock;

public class ErrorControllerTest {

	protected ErrorController controller = Mock.spy(this, ErrorController.class);

	// private ErrorPageHandler errorPageHandler;

	// @Test
	// public void testError() {
	// try {
	// controller.testError();
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (RuntimeException e) {
	//
	// }
	// }

	// @Test
	// public void error() {
	// String requestURI = "/index.do";
	//
	// MockHttpServletRequest request = new MockHttpServletRequest();
	// request.setRequestURI(requestURI);
	// request.setAttribute("javax.servlet.error.request_uri", requestURI);
	// MockHttpServletResponse response = new MockHttpServletResponse();
	// request.setAttribute("javax.servlet.error.exception", new
	// RuntimeException("error1"));
	//
	// // ErrorConfig errorConfig = errorPageHandler.findErrorInfo(uri);
	//
	// ErrorConfig errorConfig = new ErrorConfig();
	// errorConfig.setPage("/JsonView");
	// ErrorPageHandler errorPageHandler = Mockito.mock(ErrorPageHandler.class);
	// Mockito.doReturn(errorConfig).when(errorPageHandler).findErrorInfo(requestURI);
	// LeopardMockito.setProperty(controller, errorPageHandler);
	//
	// controller.error(request, response);
	// // Assert.assertEquals("未知错误.", view.getModel().get("message"));
	// }

}