package io.leopard.util;

import io.leopard.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ViewUtilTest {

	@Test
	public void ViewUtil() {
		new ViewUtil();
	}

	@Test
	public void getTextView() {
		ModelAndView model = ViewUtil.getTextView("message");
		Assert.assertEquals("message", model.getModel().get("message"));
	}

	@Test
	public void getMessageModelAndView() {
		{
			ModelAndView model = ViewUtil.getMessageModelAndView("message");
			Assert.assertEquals("message", model.getModel().get("message"));
		}
		{
			ModelAndView model = ViewUtil.getMessageModelAndView();
			Assert.assertEquals("message", model.getViewName());
		}
	}

	@Test
	public void getRedirectModelAndView() {
		@SuppressWarnings("deprecation")
		ModelAndView model = ViewUtil.getRedirectModelAndView("url");
		RedirectView redirectView = (RedirectView) model.getView();
		Assert.assertEquals("url", redirectView.getUrl());
	}

	@Test
	public void getString() {
		ModelAndView model = ViewUtil.getTextView("message");
		Assert.assertEquals("message", ViewUtil.getString(model, "message"));
		Assert.assertNull(ViewUtil.getString(model, "message1"));
	}

	@Test
	public void getInteger() {
		ModelAndView model = new ModelAndView("message");
		model.addObject("num", 1);

		Assert.assertEquals(1, (int) ViewUtil.getInteger(model, "num"));
		Assert.assertNull(ViewUtil.getInteger(model, "num1"));
	}

	@Test
	public void get() {
		ModelAndView model = new ModelAndView("message");
		model.addObject("num", 1L);

		Assert.assertEquals(1L, (long) ViewUtil.get(model, "num", Long.class));
		Assert.assertNull(ViewUtil.get(model, "num1", Long.class));

	}

	@Test
	public void list() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		ModelAndView model = new ModelAndView("message");
		model.addObject("data", list);
		List<String> list2 = ViewUtil.list(model, "data", String.class);
		Assert.assertEquals("[a, b]", list2.toString());
		Assert.assertNull(ViewUtil.list(model, "data1", String.class));
	}

	@Test
	public void getView() {
		Assert.assertNotNull(ViewUtil.getView("dir"));
	}

	@Test
	public void getObject() {
		Assert.assertNull(ViewUtil.getObject(null, "fieldName"));

		ModelAndView model = Mockito.mock(ModelAndView.class);
		Mockito.doReturn(null).when(model).getModel();
		Assert.assertNull(ViewUtil.getObject(model, "fieldName"));
	}

}