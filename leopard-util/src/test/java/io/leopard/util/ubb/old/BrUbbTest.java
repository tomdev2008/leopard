package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.BrUbb;
import org.junit.Assert;

import org.junit.Test;

public class BrUbbTest {

	@Test
	public void BrUbb() {
		new BrUbb();
	}

	@Test
	public void br() {
		String html = BrUbb.br("a[br]b");
		Assert.assertEquals("a<br />b", html);
	}

}