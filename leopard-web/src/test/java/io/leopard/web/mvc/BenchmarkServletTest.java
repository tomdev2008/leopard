package io.leopard.web.mvc;

import io.leopard.mock4j.MockRequest;
import io.leopard.mock4j.MockResponse;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;

public class BenchmarkServletTest {

	@Test
	public void BenchmarkServlet() throws ServletException, IOException {
		BenchmarkServlet servlet = new BenchmarkServlet();
		servlet.init(null);
		
		MockRequest request = new MockRequest (); 
		MockResponse response= new MockResponse(); 
		servlet.service(request, response);
	}
}