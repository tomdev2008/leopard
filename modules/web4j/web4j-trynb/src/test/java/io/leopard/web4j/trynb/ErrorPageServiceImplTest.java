package io.leopard.web4j.trynb;

import io.leopard.test4j.mock.MockRequest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ErrorPageServiceImplTest {

	protected ErrorPageServiceImpl errorPageService = new ErrorPageServiceImpl();

	@Test
	public void add() {
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/user/");
			errorPageService.add(errorConfig);
		}
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/user/info.do");
			errorPageService.add(errorConfig);
		}

		Assert.assertNotNull(this.errorPageService.findErrorInfo("/user/index.do"));
		Assert.assertNull(this.errorPageService.findErrorInfo("/group/index.do"));
	}

	@Test
	public void error() {
		MockRequest request = new MockRequest();
		this.errorPageService.error(request, "/index.do", new Exception());
	}

	@Test
	public void parseExceptionConfig() {
		ErrorPageServiceImpl errorPageService = new ErrorPageServiceImpl();
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/user/");
			List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
			ExceptionConfig exceptionConfig = new ExceptionConfig();
			exceptionConfig.setType("RuntimeException");
			exceptionConfigList.add(exceptionConfig);
			errorConfig.setExceptionConfigList(exceptionConfigList);
			errorPageService.add(errorConfig);
		}
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/");
			List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
			errorConfig.setExceptionConfigList(exceptionConfigList);
			errorPageService.add(errorConfig);
		}
		Assert.assertNotNull(errorPageService.parseExceptionConfig("/user/index.do", new RuntimeException()));
		Assert.assertNull(errorPageService.parseExceptionConfig("/group/index.do", new RuntimeException()));
	}

	// @Test
	// public void parseStatusCode() {
	// ErrorPageServiceImpl errorPageService = new ErrorPageServiceImpl();
	// {
	// ErrorConfig errorConfig = new ErrorConfig();
	// errorConfig.setUrl("/user/");
	// List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
	// ExceptionConfig exceptionConfig = new ExceptionConfig();
	// exceptionConfig.setType("RuntimeException");
	// exceptionConfig.setStatusCode("400");
	// exceptionConfigList.add(exceptionConfig);
	// errorConfig.setExceptionConfigList(exceptionConfigList);
	// errorPageService.add(errorConfig);
	// }
	// {
	// ErrorConfig errorConfig = new ErrorConfig();
	// errorConfig.setUrl("/");
	// List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
	// errorConfig.setExceptionConfigList(exceptionConfigList);
	// errorPageService.add(errorConfig);
	// }
	// MockRequest request = new MockRequest();
	// Assert.assertEquals(400, errorPageService.parseStatusCode(request, "/user/index.do", new RuntimeException()));
	// Assert.assertEquals(-599, errorPageService.parseStatusCode(request, "/group/index.do", new RuntimeException()));
	// }

	@Test
	public void parseErrorPage() {
		ErrorPageServiceImpl errorPageService = new ErrorPageServiceImpl();
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/user/");
			List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
			ExceptionConfig exceptionConfig = new ExceptionConfig();
			exceptionConfig.setType("RuntimeException");
			exceptionConfig.setStatusCode("400");
			exceptionConfigList.add(exceptionConfig);
			errorConfig.setExceptionConfigList(exceptionConfigList);
			errorPageService.add(errorConfig);
		}
		{
			ErrorConfig errorConfig = new ErrorConfig();
			errorConfig.setUrl("/");
			List<ExceptionConfig> exceptionConfigList = new ArrayList<ExceptionConfig>();
			errorConfig.setExceptionConfigList(exceptionConfigList);
			errorPageService.add(errorConfig);
		}
		MockRequest request = new MockRequest();
		Assert.assertEquals(400, errorPageService.parseErrorPage(request, "/user/index.do", new RuntimeException()).getStatusCode());
		Assert.assertEquals(-599, errorPageService.parseErrorPage(request, "/group/index.do", new RuntimeException()).getStatusCode());
	}

}