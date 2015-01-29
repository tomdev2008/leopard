package io.leopard.test4j.mock;

import org.springframework.mock.web.MockHttpServletRequest;

public class MockRequest extends MockHttpServletRequest {

	
	public void setSessionAttribute(String name, Object value) {
		this.getSession().setAttribute(name, value);
	}
}
