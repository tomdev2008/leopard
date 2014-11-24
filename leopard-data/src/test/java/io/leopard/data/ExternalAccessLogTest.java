package io.leopard.data;

import io.leopard.test4j.mock.MockRequest;
import io.leopard.util.MonitorContext;

import org.junit.Test;

public class ExternalAccessLogTest {

	@Test
	public void ExternalAccessLog() {
		new ExternalAccessLog();
	}

	@Test
	public void debug() {
		MockRequest request = new MockRequest();
		MonitorContext.setRequest(request);
		ExternalAccessLog.debug(1, "name");

		request.setRequestURI("/index.do");
		request.addHeader("X-Real-IP", "127.0.0.2");

		ExternalAccessLog.debug(1, "name");
	}

}