package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.CopyUbb;
import org.junit.Assert;

import org.junit.Test;

public class CopyUbbTest {

	@Test
	public void getPDLink() {
		Assert.assertEquals("<a href='javascript:void(0);' onclick='YyGameClient.copyCode(this.innerHTML);return false;'>复制</a>", CopyUbb.getPDLink("[copy]复制[/copy]"));
	}

	@Test
	public void CopyUbb() {
		new CopyUbb();
	}

}