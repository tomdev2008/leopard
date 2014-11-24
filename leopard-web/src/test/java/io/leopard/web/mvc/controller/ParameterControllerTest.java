package io.leopard.web.mvc.controller;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.core.exception.StatusCodeException;
import io.leopard.test.mock.MockTests;
import io.leopard.web4j.view.JsonView;
import io.leopard.web4j.view.PagingJsonView;
import io.leopard.web4j.view.TextView;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

//@SuppressWarnings("deprecation")
public class ParameterControllerTest extends MockTests {

	protected ParameterController parameterController = new ParameterController();

	@Test
	public void mapping() {
		Assert.assertEquals("mapping", parameterController.mapping().getMessage());
	}

	@Test
	public void parameter() {
		TextView textView = parameterController.parameter("uri", "IE", false, 1, "sid");
		Assert.assertTrue(textView.getMessage().startsWith("pageId:1"));
	}

	// @Test
	// public void jsonp() {
	// JsonpView jsonpView = parameterController.jsonp("uri", "IE", 1);
	// @SuppressWarnings("unchecked")
	// Map<String, Object> map = (Map<String, Object>) jsonpView.getData();
	// Assert.assertEquals("IE", map.get("userAgent"));
	// }

	@Test
	public void json() {
		JsonView jsonView = parameterController.json("uri", "IE", 1, false);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) jsonView.getData();
		Assert.assertEquals("IE", map.get("userAgent"));
		try {
			parameterController.json("uri", "IE", 1, true);
			Assert.fail("怎没没有抛异常?");
		}
		catch (StatusCodeException e) {

		}
	}

	@Test
	public void pagingJson() {
		PagingJsonView jsonView = parameterController.pagingJson("uri", "IE", 1);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) jsonView.getData();
		Assert.assertEquals("IE", map.get("userAgent"));
	}

	@Test
	public void integer() {
		TextView textView = parameterController.integer(1, 1);
		Assert.assertEquals("cid:1 pageid:1", textView.getMessage());

	}

	@Test
	public void int2() {
		TextView textView = parameterController.int2(1, 1);
		Assert.assertEquals("cid:1 pageid:1", textView.getMessage());
	}

	@Test
	public void datatype() {
		TextView textView = parameterController.datatype(new OnlyDate(), new Month());
		Assert.assertTrue(textView.getMessage().startsWith("onlyDate"));
	}

	@Test
	public void cookie() {
		TextView textView = parameterController.cookie("hctan");
		Assert.assertTrue(textView.getMessage().startsWith("cookieLoginedUsername"));
	}

	@Test
	public void session() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		TextView textView = parameterController.session(request);
		Assert.assertTrue(textView.getMessage().startsWith("sid:"));
	}
}