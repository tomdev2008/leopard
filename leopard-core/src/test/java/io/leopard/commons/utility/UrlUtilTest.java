package io.leopard.commons.utility;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class UrlUtilTest {

	@Test
	public void hostOfUrl() {
		Assert.assertEquals("www.yy.com", UrlUtil.hostOfUrl("http://www.yy.com/index.do"));
	}

	@Test
	public void appendUriMethod2Url() {
		String url1 = "http://www.baidu.com:8080/a/";
		String url2 = "/b/c/a.do";
		Assert.assertEquals("http://www.baidu.com:8080/a/b/c/a.do", UrlUtil.appendUriMethod2Url(url1, url2));

	}

	@Test
	public void appendParams2Url() {
		Map.Entry<String, Object> entry1 = new SimpleEntry<String, Object>("key1", "value1");
		Map.Entry<String, Object> entry2 = new SimpleEntry<String, Object>("key2", "value2");

		@SuppressWarnings("unchecked")
		String url = UrlUtil.appendParams2Url("http://www.yy.com/index.do", entry1, entry2);
		// System.out.println("url :" + url);
		Assert.assertEquals("http://www.yy.com/index.do?key1=value1&key2=value2", url);
	}

	@Test
	public void UrlUtil() {
		new UrlUtil();
	}
}