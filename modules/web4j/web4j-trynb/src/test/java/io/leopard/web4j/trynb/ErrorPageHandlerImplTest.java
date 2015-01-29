package io.leopard.web4j.trynb;

import io.leopard.core.exception.StatusCodeException;
import io.leopard.core.exception.other.OutSideException;
import io.leopard.test4j.mock.MockRequest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ErrorPageHandlerImplTest {

	ErrorPageService errorPageService = Mockito.mock(ErrorPageService.class);

	protected ErrorPageHandlerImpl errorPageHandler = newInstance();

	protected ErrorPageHandlerImpl newInstance() {
		ErrorPageHandlerImpl errorPageHandler = new ErrorPageHandlerImpl();
		ErrorPageHandlerImpl.setErrorPageService(errorPageService);
		return errorPageHandler;
	}

	//
	@Test
	public void ErrorPageHandlerImpl() {

	}

	@Test
	public void findErrorInfo() {
		errorPageHandler.findErrorInfo("/index.do");
	}

	@Test
	public void parseErrorPage() {
		MockRequest request = new MockRequest();
		errorPageHandler.parseErrorPage(request, "/index.do", new Exception());
	}

	@Test
	public void toOkTextView() {
		MockRequest request = new MockRequest();
		Assert.assertEquals("560:err", errorPageHandler.toOkTextView(request, "/index.do", new OutSideException("err")).getMessage());
		Assert.assertEquals("java.lang.NullPointerException", errorPageHandler.toOkTextView(request, "/index.do", new NullPointerException()).getMessage());
		Assert.assertEquals("errmsg", errorPageHandler.toOkTextView(request, "/index.do", new NullPointerException("errmsg")).getMessage());
	}

	@Test
	public void toJsonView() {
		MockRequest request = new MockRequest();
		errorPageHandler.toJsonView(request, "/index.do", new StatusCodeException("403", "errmsg", "errmsg"));

		ErrorPage errorPage = Mockito.mock(ErrorPage.class);

		Exception e = new Exception("errmsg");
		Mockito.doReturn(errorPage).when(errorPageService).parseErrorPage(request, "/index.do", e);
		// errorPageService.parseErrorPage(request, uri, exception);

		errorPageHandler.toJsonView(request, "/index.do", e);
	}

	// @Test
	// public void toWebserviceView() {
	// MockRequest request = new MockRequest();
	// errorPageHandler.toWebserviceView(request, "/index.do", new
	// StatusCodeException(403, "errmsg"));
	//
	// ErrorPage errorPage = Mockito.mock(ErrorPage.class);
	//
	// Exception e = new Exception("errmsg");
	// Mockito.doReturn(errorPage).when(errorPageService).parseErrorPage(request,
	// "/index.do", e);
	// // errorPageService.parseErrorPage(request, uri, exception);
	//
	// errorPageHandler.toWebserviceView(request, "/index.do", e);
	// }
}