package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.ColorUbb;
import org.junit.Assert;

import org.junit.Test;

public class ColorUbbTest {

	@Test
	public void getCSLink() {
		// sb.append("<span style='color:");
		// sb.append(str);
		// sb.append("'>");
		// sb.append(temp);
		// sb.append("</span>");

		Assert.assertEquals("<span style='color:#ff00ff'>color</span>", ColorUbb.getCSLink("[color=#ff00ff]color[/color]"));

	}

	@Test
	public void ColorUbb() {
		new ColorUbb();
	}
}