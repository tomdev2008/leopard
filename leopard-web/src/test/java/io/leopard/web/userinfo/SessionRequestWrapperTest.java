package io.leopard.web.userinfo;

import io.leopard.test4j.mock.MockRequest;
import io.leopard.test4j.mock.MockResponse;

import org.junit.Assert;
import org.junit.Test;

public class SessionRequestWrapperTest {

	@Test
	public void setExpiry() {
		try {
			SessionRequestWrapper.setExpiry(99);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		SessionRequestWrapper.setExpiry(3600);

	}

	@Test
	public void SessionRequestWrapper() {

	}

	@Test
	public void createJsessionIdCookie() {

	}

	@Test
	public void getSession() {

		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		SessionRequestWrapper sessionRequestWrapper = new SessionRequestWrapper(request, response, null);

		sessionRequestWrapper.getSession();
	}

}