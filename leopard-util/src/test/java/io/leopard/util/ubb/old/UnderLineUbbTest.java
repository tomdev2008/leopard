package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.UnderLineUbb;
import org.junit.Assert;

import org.junit.Test;

public class UnderLineUbbTest {

	@Test
	public void replaceCUstr() {
		Assert.assertEquals("<u>下划线</u>", UnderLineUbb.replaceCUstr("[u]下划线[/u]"));
	}

	@Test
	public void UnderLineUbb() {
		new UnderLineUbb();
	}

	@Test
	public void underLine() {
		Assert.assertEquals("<u>text</u>", UnderLineUbb.underLine("[u]text[/u]"));
	}

}