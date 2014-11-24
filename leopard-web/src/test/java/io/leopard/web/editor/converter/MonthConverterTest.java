package io.leopard.web.editor.converter;

import org.junit.Assert;
import org.junit.Test;

public class MonthConverterTest {

	@Test
	public void convert() {
		MonthConverter converter = new MonthConverter();
		Assert.assertEquals("2013-01", converter.convert("2013-01-01").toString());
	}

}