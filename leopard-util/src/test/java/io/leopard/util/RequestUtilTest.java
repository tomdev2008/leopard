package io.leopard.util;

import io.leopard.test4j.mock.MockRequest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class RequestUtilTest {

	@Test
	public void RequestUtil() {
		new RequestUtil();
	}

	@Test
	public void getProxyIp() {
		{
			MockHttpServletRequest request = new MockHttpServletRequest();
			request.addHeader("X-Real-IP", "127.0.0.3");
			Assert.assertEquals("127.0.0.3", RequestUtil.getProxyIp(request));
		}
		{
			MockHttpServletRequest request = new MockHttpServletRequest();
			request.addHeader("RealIP", "127.0.0.2");
			Assert.assertEquals("127.0.0.2", RequestUtil.getProxyIp(request));
		}
		{
			MockHttpServletRequest request = new MockHttpServletRequest();
			request.setRemoteAddr("127.0.0.1");
			Assert.assertEquals("127.0.0.1", RequestUtil.getProxyIp(request));
		}
	}

	@Test
	public void getRequestContextUri() {
		MockRequest request = new MockRequest();
		{
			request.setContextPath("/");
			request.setRequestURI("/monitor/monitor.do");
			Assert.assertEquals("/monitor/monitor.do", RequestUtil.getRequestContextUri(request));
			request.setRequestURI("//monitor/monitor.do");
			Assert.assertEquals("/monitor/monitor.do", RequestUtil.getRequestContextUri(request));
		}
		{
			request.setContextPath("/admin");
			request.setRequestURI("/admin//monitor/monitor.do");
			Assert.assertEquals("/monitor/monitor.do", RequestUtil.getRequestContextUri(request));
		}

	}
}