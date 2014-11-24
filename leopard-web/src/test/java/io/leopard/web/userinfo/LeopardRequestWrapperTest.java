package io.leopard.web.userinfo;

import io.leopard.mock4j.MockRequest;
import io.leopard.mock4j.MockResponse;
import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.web4j.session.SessionServiceImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ SessionServiceImpl.class })
public class LeopardRequestWrapperTest {

	@Test
	public void getParameterValues() {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		LeopardRequestWrapper userinfoWrapper = new LeopardRequestWrapper(request, response, null);
		// request.setSessionAttribute("sessUsername", "hctan");
		// Assert.assertEquals("hctan",
		// userinfoWrapper.getParameterValues("sessUsername")[0]);

		request.setParameter("name1", "value1");
		Assert.assertEquals("value1", userinfoWrapper.getParameterValues("name1")[0]);
	}

	@Test
	public void getParameter() {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		LeopardRequestWrapper userinfoWrapper = new LeopardRequestWrapper(request, response, null);
		// request.setSessionAttribute("sessUsername", "hctan");
		// Assert.assertEquals("hctan",
		// userinfoWrapper.getParameter("sessUsername"));

		request.setParameter("name1", "value1");
		Assert.assertEquals("value1", userinfoWrapper.getParameter("name1"));
		Assert.assertNull(userinfoWrapper.getParameter("name2"));
	}

	@Test
	public void getSession() {
		MockRequest request = new MockRequest();
		MockResponse response = new MockResponse();
		LeopardRequestWrapper userinfoWrapper = new LeopardRequestWrapper(request, response, null);
		userinfoWrapper.getSession();
		userinfoWrapper.getSession(true);
		userinfoWrapper.hasSessionRequestWrapper = true;
		userinfoWrapper.getSession();
		userinfoWrapper.getSession(true);
	}

}