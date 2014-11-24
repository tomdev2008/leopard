package io.leopard.web.interceptor;

import io.leopard.mock4j.MockRequest;
import io.leopard.test.mock.Mock;
import io.leopard.test.mock.MockTests;

import org.junit.Test;

public class MonitorPermissionInterceptorTest extends MockTests {

	protected MonitorPermissionInterceptor monitorPermissionInterceptor = Mock.spy(this, MonitorPermissionInterceptor.class);

	@Test
	public void preHandle() throws Exception {
		monitorPermissionInterceptor.preHandle("/webservice/", null, null, null);

		MockRequest request = new MockRequest();
		
		monitorPermissionInterceptor.preHandle("/monitor/", request, null, null);
		monitorPermissionInterceptor.preHandle("/monitor/api.do", request, null, null);

	}

}