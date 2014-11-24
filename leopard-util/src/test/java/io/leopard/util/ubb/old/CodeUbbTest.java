package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.CodeUbb;
import org.junit.Assert;

import org.junit.Test;

public class CodeUbbTest {

	@Test
	public void getCodeLink() {
		Assert.assertEquals("<a href='javascript:void(0);' onclick='YyGameClient.copyCode(this.innerHTML);'>code</a>", CodeUbb.getCodeLink("[code]code[/code]"));
	}

	@Test
	public void CodeUbb() {
		new CodeUbb();
	}

}