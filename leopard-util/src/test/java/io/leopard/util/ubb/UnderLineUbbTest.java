package io.leopard.util.ubb;

import io.leopard.util.ubb.UnderLineUbb;
import org.junit.Assert;

import org.junit.Test;

public class UnderLineUbbTest {

	@Test
	public void parse() {
		Assert.assertEquals("<u>下划线</u>", new UnderLineUbb().parse("[u]下划线[/u]"));
	}

}