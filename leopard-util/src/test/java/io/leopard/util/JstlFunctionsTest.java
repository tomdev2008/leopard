package io.leopard.util;

import io.leopard.commons.utility.StringUtil;
import io.leopard.util.JstlFunctions;
import org.junit.Assert;

import org.junit.Test;

public class JstlFunctionsTest {

	@Test
	public void urlEncode() {
		String encode = JstlFunctions.urlEncode("中文");
		Assert.assertEquals("中文", StringUtil.urlDecode(encode));
	}

	@Test
	public void JstlFunctions() {
		new JstlFunctions();
	}

	@Test
	public void escapeJavascript() {
		Assert.assertEquals("\\\"content\\\"", JstlFunctions.escapeJavascript("\"content\""));
	}

	@Test
	public void escapeJavascriptParam() {
		Assert.assertNull(JstlFunctions.escapeJavascriptParam(null));
		Assert.assertEquals("content", JstlFunctions.escapeJavascriptParam("\"content"));
		Assert.assertEquals("content", JstlFunctions.escapeJavascriptParam("\"content\""));
		Assert.assertEquals("\\'content\\'", JstlFunctions.escapeJavascriptParam("'content'"));

	}

	@Test
	public void escapeHTMLTags() {
		Assert.assertEquals("&lt;a&gt;", JstlFunctions.escapeHTMLTags("<a>"));

	}

}