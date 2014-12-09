package io.leopard.web.userinfo.util;

import io.leopard.test.mock.MockTests;
import io.leopard.web4j.view.RequestUtil;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class RequestUtilTest extends MockTests {

	@Test
	public void getReferer() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("referer", "http://yy.com/");
		RequestUtil.getReferer(request);
	}

	@Test
	public void getPageid() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		{
			request.setParameter("page", "1");
			Assert.assertEquals(1, RequestUtil.getPageid(request));
		}
		{
			request.setParameter("page", "2");
			Assert.assertEquals(2, RequestUtil.getPageid(request));
		}
		{
			request.setParameter("page", "");
			Assert.assertEquals(1, RequestUtil.getPageid(request));
		}
		{
			Assert.assertEquals(1, RequestUtil.getPageid(0));

			Assert.assertEquals(1, RequestUtil.getPageid((Integer) null));
			Assert.assertEquals(2, RequestUtil.getPageid(2));
		}
	}

	@Test
	public void isLicitIp() {
		Assert.assertFalse(RequestUtil.isLicitIp(null));
		Assert.assertFalse(RequestUtil.isLicitIp(""));
		Assert.assertTrue(RequestUtil.isLicitIp("127.0.0.1"));
	}

	@Test
	public void getCookieUsername() {
		Cookie cookie = new Cookie("username", "hctan");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(cookie);
		Assert.assertEquals("hctan", RequestUtil.getCookieUsername(request));
	}

	@Test
	public void getProxyIp() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		{
			request.addHeader("RealIP", "127.0.0.2");
			Assert.assertEquals("127.0.0.2", RequestUtil.getProxyIp(request));
		}
		{
			request.addHeader("X-Real-IP", "127.0.0.3");
			Assert.assertEquals("127.0.0.3", RequestUtil.getProxyIp(request));
		}
	}

	@Test
	public void getUserAgent() {
		// user-agent
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("user-agent", "IE");
		Assert.assertEquals("IE", RequestUtil.getUserAgent(request));
	}

	@Test
	public void getDomain() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("yy.com");
		Assert.assertEquals("http://yy.com", RequestUtil.getDomain(request));
	}

	@Test
	public void getRequestContextUri() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		{
			request.setRequestURI("/index.do");
			Assert.assertEquals("/index.do", RequestUtil.getRequestContextUri(request));
		}
		{

			request.setContextPath("/leopard");
			request.setRequestURI("/leopard/index.do");
			// String url = RequestUtil.getRequestContextUri(request);
			// System.out.println("url:" +
			// RequestUtil.getRequestContextUri(request));
			Assert.assertEquals("/index.do", RequestUtil.getRequestContextUri(request));
		}
		{

			request.setContextPath("/");
			request.setRequestURI("/index.do");
			Assert.assertEquals("/index.do", RequestUtil.getRequestContextUri(request));
		}
	}

	@Test
	public void getString() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		Assert.assertEquals("", RequestUtil.getString(request, "name", ""));
		request.setParameter("name", "value");
		Assert.assertEquals("value", RequestUtil.getString(request, "name", ""));
	}

	@Test
	public void RequestUtil() {
		new RequestUtil();
	}

	@Test
	public void printHeaders() {

		RequestUtil.printHeaders(new MockHttpServletRequest());
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("name1", "value1");
		RequestUtil.printHeaders(request);

	}

	@Test
	public void printAttributes() {
		RequestUtil.printAttributes(new MockHttpServletRequest());
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setAttribute("name", "value");
		RequestUtil.printAttributes(request);
	}

	@Test
	public void getFile() {
		{
			MultipartHttpServletRequest request = Mockito.mock(MultipartHttpServletRequest.class);
			Assert.assertNull(RequestUtil.getFile(request, "name"));
		}
		{
			MultipartHttpServletRequest request = Mockito.mock(MultipartHttpServletRequest.class);
			MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
			Mockito.doReturn(true).when(multipartFile).isEmpty();
			Mockito.doReturn(multipartFile).when(request).getFile("name");
			Assert.assertNull(RequestUtil.getFile(request, "name"));
		}
		{
			MultipartHttpServletRequest request = Mockito.mock(MultipartHttpServletRequest.class);
			MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
			Mockito.doReturn(false).when(multipartFile).isEmpty();
			Mockito.doReturn(multipartFile).when(request).getFile("name");
			Assert.assertNotNull(RequestUtil.getFile(request, "name"));
		}
	}
}