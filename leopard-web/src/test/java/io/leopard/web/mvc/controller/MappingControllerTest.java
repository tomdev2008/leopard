package io.leopard.web.mvc.controller;

import io.leopard.test.mock.MockTests;
import io.leopard.web4j.view.TextView;

import org.junit.Assert;
import org.junit.Test;

public class MappingControllerTest extends MockTests {

	protected MappingController controller = new MappingController();

	@Test
	public void index() {
		TextView textView = controller.index();
		Assert.assertEquals("Hello Mapping", textView.getMessage());
	}

	@Test
	public void welcome() {
		TextView textView = controller.welcome();
		Assert.assertEquals("Welcome Mapping", textView.getMessage());
	}

}