package io.leopard.schema;

import org.junit.Assert;
import org.junit.Test;

public class ElementImplTest {

	@Test
	public void ElementImpl() {
		ElementImpl element = new ElementImpl();

		element.setAttribute("name", "value");

		Assert.assertEquals("value", element.getAttribute("name"));
		element.removeAttribute("name");
		Assert.assertNull(element.getAttribute("name"));
	}

}