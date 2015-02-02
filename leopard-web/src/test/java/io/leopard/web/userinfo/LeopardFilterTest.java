package io.leopard.web.userinfo;

import io.leopard.test4j.mock.MockRequest;
import io.leopard.test4j.mock.MockResponse;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.Test;
import org.mockito.Mockito;

//@RunWith(LeopardMockRunner.class)
//@PrepareForTest({ MonitorBeanFactory.class, LeopardWebTimeLog.class })
public class LeopardFilterTest {

	LeopardFilter filter = newInstance();

	protected LeopardFilter newInstance() {

		LeopardFilter filter = new LeopardFilter();
		return filter;
	}

	@Test
	public void init() throws ServletException {
		filter.init(null);
	}

	@Test
	public void destroy() {
		filter.destroy();
	}

	// @Test
	// public void loging() throws ServletException, IOException {
	// MockRequest request = Mockito.spy(new MockRequest());
	// MockResponse response = new MockResponse();
	// UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
	// LeopardFilter filter = Mockito.spy(new LeopardFilter());
	// LeopardMockito.setProperty(filter, userinfoService);
	//
	// filter.loging(request, response);
	//
	// AdminService adminLoginService = Mockito.mock(AdminService.class);
	//
	// LeopardMockito.setProperty(filter, adminLoginService);
	//
	// RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
	// Mockito.doReturn(requestDispatcher).when(request).getRequestDispatcher("/loging.do");
	// Mockito.doThrow(new RuntimeException()).when(requestDispatcher).forward(request, response);
	// try {
	// filter.loging(request, response);
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (RuntimeException e) {
	//
	// }
	//
	// // Mockito.doReturn(true).when(adminLoginService).isAdminFolder(request);
	// // Assert.assertFalse(filter.loging(request, response));
	// }

	// @Test
	// public void forwardLoginUrl() {
	// UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
	// UserinfoFilter filter = Mockito.spy(new UserinfoFilter());
	// LeopardMockito.setProperty(filter, userinfoService);
	//
	// MockRequest request = new MockRequest();
	// MockResponse response = new MockResponse();
	// filter.forwardLoginUrl(request, response);
	// filter.forwardLoginUrl(request, response);
	//
	// Mockito.doReturn(true).when(filter).loging(request, response);
	// filter.forwardLoginUrl(request, response);
	// Mockito.doReturn(false).when(filter).loging(request, response);
	// filter.forwardLoginUrl(request, response);
	// }

	// @Test
	// public void checkLogin() {
	// UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
	//
	// LeopardFilter filter = new LeopardFilter();
	// LeopardMockito.setProperty(filter, userinfoService);
	//
	// MockRequest request = new MockRequest();
	// MockResponse response = new MockResponse();
	// Mockito.doReturn(true).when(userinfoService).isExcludeUri(request);
	// filter.checkLogin(request, response);
	//
	// Mockito.doReturn(false).when(userinfoService).isExcludeUri(request);
	// filter.checkLogin(request, response);
	//
	// filter.checkLogin(request, response);
	//
	// Mockito.doReturn(null).when(userinfoService).login(request, response);
	// Assert.assertFalse(filter.checkLogin(request, response));
	//
	// Mockito.doReturn("hctan").when(userinfoService).login(request, response);
	// Assert.assertTrue(filter.checkLogin(request, response));
	//
	// ConnectionLimitDao connectionLimitDao =
	// Mockito.mock(ConnectionLimitDao.class);
	// LeopardMockito.setProperty(filter, connectionLimitDao);
	//
	// Assert.assertTrue(filter.checkLogin(request, response));
	//
	// }

	// @Test
	// public void doFilter2() throws IOException, ServletException {
	// MockRequest request = new MockRequest();
	// MockResponse response = new MockResponse();
	// FilterChain chain = Mockito.mock(FilterChain.class);
	//
	// UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
	// LeopardFilter filter = Mockito.spy(new LeopardFilter());
	// LeopardMockito.setProperty(filter, userinfoService);
	//
	// LeopardWebTimeLog.start();
	// filter.doFilter2(request, response, chain);
	//
	// Mockito.doReturn(false).when(filter).checkLogin(Mockito.any(LeopardRequestWrapper.class), Mockito.any(HttpServletResponse.class));
	// filter.doFilter2(request, response, chain);
	//
	// Assert.assertNull(filter.adminService);
	//
	// AdminService adminLoginService = Mockito.mock(AdminService.class);
	// LeopardMockito.setProperty(filter, adminLoginService);
	//
	// // Assert.assertNotNull(filter.adminLoginService);
	// // Mockito.doReturn(true).when(adminLoginService).isAdminFolder(Mockito.any(UserinfoWrapper.class));
	// // filter.doFilter2(request, response, chain);
	// //
	// // Mockito.doReturn(false).when(adminLoginService).isAdminFolder(Mockito.any(UserinfoWrapper.class));
	// // filter.doFilter2(request, response, chain);
	// }

	@Test
	public void doFilter() throws IOException, ServletException {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		FilterChain chain = Mockito.mock(FilterChain.class);

		LeopardFilter filter = Mockito.spy(new LeopardFilter());

		filter.doFilter(request, response, chain);
		// Mockito.doReturn(true).when(userinfoService).isEnableTimeLog();
		filter.doFilter(request, response, chain);
		request.setQueryString("queryString");
		filter.doFilter(request, response, chain);
	}

}