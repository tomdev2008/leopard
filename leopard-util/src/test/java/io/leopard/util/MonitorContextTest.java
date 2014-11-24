package io.leopard.util;

import io.leopard.test4j.mock.MockRequest;

import org.junit.Assert;
import org.junit.Test;

public class MonitorContextTest {

	@Test
	public void MonitorContext() {
		new MonitorContext();
	}

	@Test
	public void setRequest() {
		MockRequest request = new MockRequest();
		MonitorContext.setRequest(request);
		Assert.assertNotNull(MonitorContext.getRequest());
	}

	@Test
	public void getProxyIp() {
		MockRequest request = new MockRequest();
		request.addHeader("X-Real-IP", "127.0.0.2");

		MonitorContext.setRequest(request);
		Assert.assertEquals("127.0.0.2", MonitorContext.getProxyIp());

		MonitorContext.setRequest(null);
		Assert.assertNull(MonitorContext.getProxyIp());
	}

	@Test
	public void getRequestUri() {
		MockRequest request = new MockRequest();
		request.setRequestURI("/index.do");

		MonitorContext.setRequest(request);
		Assert.assertEquals("/index.do", MonitorContext.getRequestUri());

		MonitorContext.setRequest(null);
		Assert.assertNull(MonitorContext.getRequestUri());
	}

}