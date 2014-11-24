package io.leopard.web.userinfo.util;

import io.leopard.test.mock.Mock;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;

public class ResponseUtilTest {

	@Test
	public void ResponseUtil() {
		new ResponseUtil();
	}

	@Test
	public void sendRedirect() throws IOException {
		MockHttpServletResponse response = Mockito.spy(new MockHttpServletResponse());
		ResponseUtil.sendRedirect("url", response);
		Mock.verify(response).sendRedirect("url");
	}

	@Test
	public void sendRedirect2() throws IOException {
		MockHttpServletResponse response = Mockito.spy(new MockHttpServletResponse());
		Mockito.doThrow(new IOException("")).when(response).sendRedirect("url");
		try {
			ResponseUtil.sendRedirect("url", response);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {
		}
		// Mock.verify(response).sendRedirect("url");
	}
}