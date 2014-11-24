package io.leopard.util.ubb;

import io.leopard.util.ubb.ColorUbb;
import org.junit.Assert;

import org.junit.Test;

public class ColorUbbTest {

	@Test
	public void parse() {
		Assert.assertEquals("<span style='color:#ff00ff'>color</span>", new ColorUbb().parse("[color=#ff00ff]color[/color]"));

	}

}