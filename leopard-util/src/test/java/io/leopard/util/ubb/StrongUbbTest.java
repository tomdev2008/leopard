package io.leopard.util.ubb;

import io.leopard.util.ubb.StrongUbb;
import org.junit.Assert;

import org.junit.Test;

public class StrongUbbTest {

	@Test
	public void parse() {
		Assert.assertEquals("<b>b</b>", new StrongUbb().parse("[b]b[/b]"));
	}
}