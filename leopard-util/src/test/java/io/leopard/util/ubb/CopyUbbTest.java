package io.leopard.util.ubb;

import io.leopard.util.ubb.CopyUbb;
import org.junit.Assert;

import org.junit.Test;

public class CopyUbbTest {

	@Test
	public void parse() {
		CopyUbb ubb = new CopyUbb();
		String html = ubb.parse("[copy]text[/copy]");
		System.out.println("html:" + html);
		Assert.assertTrue(html.indexOf("copyCode") != -1);
	}

}