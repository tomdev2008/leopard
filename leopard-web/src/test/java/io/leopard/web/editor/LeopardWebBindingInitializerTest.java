package io.leopard.web.editor;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

public class LeopardWebBindingInitializerTest {

	@Test
	public void initBinder() {
		WebDataBinder binder = Mockito.mock(WebDataBinder.class);
		WebRequest request = Mockito.mock(WebRequest.class);
		LeopardWebBindingInitializer initializer = new LeopardWebBindingInitializer();
		initializer.initBinder(binder, request);
	}

}