package io.leopard.web.interceptor;

import io.leopard.mock4j.MockRequest;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.MockTests;
import io.leopard.test4j.mock.LeopardMockito;
import io.leopard.web.userinfo.UriListChecker;

import org.junit.Test;
import org.mockito.Mockito;

public class ConnectionLimitInterceptorTest extends MockTests {

	protected ConnectionLimitInterceptor connectionLimitInterceptor = Mock.spy(this, ConnectionLimitInterceptor.class);
	private UriListChecker includeConnectionLimitUriListChecker;

	@Test
	public void init() {
		connectionLimitInterceptor.init();
	}

	@Test
	public void preHandle() throws Exception {
		this.connectionLimitInterceptor.preHandle("/index.do", null, null, null);

		MockRequest request = new MockRequest();
		Mockito.doReturn(true).when(includeConnectionLimitUriListChecker).exists("/index.do");
		this.connectionLimitInterceptor.preHandle("/index.do", request, null, null);

		ConnectionLimitInterceptor.setAccount(request, "hctan");
		this.connectionLimitInterceptor.preHandle("/index.do", request, null, null);

		LeopardMockito.setProperty(this.connectionLimitInterceptor, null, "connectionLimitDao");
		this.connectionLimitInterceptor.preHandle("/index.do", request, null, null);
	}

}