package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.UrlUbb;
import org.junit.Assert;

import org.junit.Test;

public class UrlUbbTest {

	@Test
	public void UrlUbb() {
		new UrlUbb();
	}

	@Test
	public void getPALink() {
		String html = UrlUbb.getPALink("[url]http://yy.com/,title[/url]");
		System.out.println("html:" + html);
		Assert.assertTrue(html.indexOf("</a>") != -1);
	}

}