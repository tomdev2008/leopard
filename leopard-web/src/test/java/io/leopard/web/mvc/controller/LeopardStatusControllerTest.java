package io.leopard.web.mvc.controller;

import io.leopard.test.mock.MockTests;
import io.leopard.test4j.mock.LeopardMockito;
import io.leopard.web4j.view.TextView;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class LeopardStatusControllerTest extends MockTests {

	protected LeopardStatusController controller = new LeopardStatusController();

	@Test
	public void status() {
		LeopardStatusService leopardStatusService = Mockito.mock(LeopardStatusService.class);
		LeopardMockito.setProperty(controller, leopardStatusService);

		TextView textView = controller.status();
		Assert.assertTrue(textView.getMessage().startsWith("##############"));
	}

}