package io.leopard.web.interceptor;

import io.leopard.test.mock.Mock;
import io.leopard.test.mock.MockTests;
import io.leopard.test4j.mock.MockRequest;

import org.junit.Test;
import org.mockito.Mockito;

public class WebservicePermissionInterceptorTest extends MockTests {

	protected WebservicePermissionInterceptor webservicePermissionInterceptor = Mock.spy(this, WebservicePermissionInterceptor.class);

	@Test
	public void preHandle() throws Exception {
		webservicePermissionInterceptor.preHandle("/monitor/", null, null, null);

		MockRequest request = new MockRequest();
		Mockito.doReturn(true).when(webservicePermissionInterceptor).hasPermission(null);
		webservicePermissionInterceptor.preHandle("/webservice/", request, null, null);

		Mockito.doReturn(false).when(webservicePermissionInterceptor).hasPermission(null);
		webservicePermissionInterceptor.preHandle("/webservice/", request, null, null);
	}

	@Test
	public void hasPermission() {
		// AUTO
	}

}