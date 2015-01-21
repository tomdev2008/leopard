package io.leopard.util;

import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Test;

public class XmlUtilsTest {

	@Test
	public void XmlUtils() {
		new XmlUtils();
	}

	@Test
	public void cdata() {
		Assert.assertEquals("<![CDATA[content]]>", XmlUtils.cdata("content"));
	}

	@Test
	public void escape() {
		Assert.assertNull(XmlUtils.escape(null));
		Assert.assertEquals("&lt;", XmlUtils.escape("<"));
		Assert.assertEquals("&gt;", XmlUtils.escape(">"));
		Assert.assertEquals("&quot;", XmlUtils.escape("\""));
		Assert.assertEquals("&#039;", XmlUtils.escape("'"));
		Assert.assertEquals("&amp;", XmlUtils.escape("&"));
		Assert.assertEquals("abc", XmlUtils.escape("abc"));
	}

	@Test
	public void parseElement() {
		String content = "<root><name>name</name></root>";
		Element element = XmlUtils.parseElement(content);
		Assert.assertEquals("name", element.getName());
		Assert.assertEquals("name", element.getText());
	}

}