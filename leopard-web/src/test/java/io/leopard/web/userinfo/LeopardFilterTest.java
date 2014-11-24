package io.leopard.web.userinfo;

import io.leopard.ext.connectionlimit.ConnectionLimitDao;
import io.leopard.test4j.mock.LeopardMockito;
import io.leopard.test4j.mock.MockRequest;
import io.leopard.test4j.mock.MockResponse;
import io.leopard.topnb.LeopardWebTimeLog;
import io.leopard.web.userinfo.service.ConfigHandler;
import io.leopard.web.userinfo.service.UserinfoService;
import io.leopard.web4j.admin.dao.AdminLoginService;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

//@RunWith(LeopardMockRunner.class)
//@PrepareForTest({ MonitorBeanFactory.class, LeopardWebTimeLog.class })
public class LeopardFilterTest {
	private UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
	private ConfigHandler loginHandler = Mockito.mock(ConfigHandler.class);

	LeopardFilter filter = newInstance();

	protected LeopardFilter newInstance() {

		LeopardFilter filter = new LeopardFilter();
		LeopardMockito.setProperty(filter, userinfoService);
		LeopardMockito.setProperty(filter, loginHandler);
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

	@Test
	public void loging() throws ServletException, IOException {
		MockRequest request = Mockito.spy(new MockRequest());
		MockResponse response = new MockResponse();
		UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
		LeopardFilter filter = Mockito.spy(new LeopardFilter());
		LeopardMockito.setProperty(filter, userinfoService);

		filter.loging(request, response);

		AdminLoginService adminLoginService = Mockito.mock(AdminLoginService.class);

		LeopardMockito.setProperty(filter, adminLoginService);

		RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
		Mockito.doReturn(requestDispatcher).when(request).getRequestDispatcher("/loging.do");
		Mockito.doThrow(new RuntimeException()).when(requestDispatcher).forward(request, response);
		try {
			filter.loging(request, response);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}

		// Mockito.doReturn(true).when(adminLoginService).isAdminFolder(request);
		// Assert.assertFalse(filter.loging(request, response));
	}

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

	@Test
	public void checkLogin() {
		UserinfoService userinfoService = Mockito.mock(UserinfoService.class);

		LeopardFilter filter = new LeopardFilter();
		LeopardMockito.setProperty(filter, userinfoService);

		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		Mockito.doReturn(true).when(userinfoService).isExcludeUri(request);
		filter.checkLogin(request, response);

		Mockito.doReturn(false).when(userinfoService).isExcludeUri(request);
		filter.checkLogin(request, response);

		filter.checkLogin(request, response);

		Mockito.doReturn(null).when(userinfoService).login(request, response);
		Assert.assertFalse(filter.checkLogin(request, response));

		Mockito.doReturn("hctan").when(userinfoService).login(request, response);
		Assert.assertTrue(filter.checkLogin(request, response));

		ConnectionLimitDao connectionLimitDao = Mockito.mock(ConnectionLimitDao.class);
		LeopardMockito.setProperty(filter, connectionLimitDao);

		Assert.assertTrue(filter.checkLogin(request, response));

	}

	@Test
	public void doFilter2() throws IOException, ServletException {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		FilterChain chain = Mockito.mock(FilterChain.class);

		UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
		LeopardFilter filter = Mockito.spy(new LeopardFilter());
		LeopardMockito.setProperty(filter, userinfoService);

		LeopardWebTimeLog.start();
		filter.doFilter2(request, response, chain);

		Mockito.doReturn(false).when(filter).checkLogin(Mockito.any(LeopardRequestWrapper.class), Mockito.any(HttpServletResponse.class));
		filter.doFilter2(request, response, chain);

		Assert.assertNull(filter.adminLoginService);

		AdminLoginService adminLoginService = Mockito.mock(AdminLoginService.class);
		LeopardMockito.setProperty(filter, adminLoginService);

		// Assert.assertNotNull(filter.adminLoginService);
		// Mockito.doReturn(true).when(adminLoginService).isAdminFolder(Mockito.any(UserinfoWrapper.class));
		// filter.doFilter2(request, response, chain);
		//
		// Mockito.doReturn(false).when(adminLoginService).isAdminFolder(Mockito.any(UserinfoWrapper.class));
		// filter.doFilter2(request, response, chain);
	}

	@Test
	public void doFilter() throws IOException, ServletException {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		FilterChain chain = Mockito.mock(FilterChain.class);

		UserinfoService userinfoService = Mockito.mock(UserinfoService.class);
		LeopardFilter filter = Mockito.spy(new LeopardFilter());
		LeopardMockito.setProperty(filter, userinfoService);

		filter.doFilter(request, response, chain);
		// Mockito.doReturn(true).when(userinfoService).isEnableTimeLog();
		filter.doFilter(request, response, chain);
		request.setQueryString("queryString");
		filter.doFilter(request, response, chain);
	}

}