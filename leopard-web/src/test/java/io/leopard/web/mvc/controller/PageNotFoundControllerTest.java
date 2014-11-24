package io.leopard.web.mvc.controller;

import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.web4j.trynb.ErrorUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ErrorUtil.class })
public class PageNotFoundControllerTest {

	protected PageNotFoundController controller = new PageNotFoundController();

	@Test
	public void pageNotFound() {

		// PowerMockito.when(ErrorUtil.isUseFtl()).thenReturn(true);
		// MockHttpServletRequest request = new MockHttpServletRequest();
		// ModelAndView view = controller.pageNotFound(request);
		// Assert.assertNotNull(view);
	}

}