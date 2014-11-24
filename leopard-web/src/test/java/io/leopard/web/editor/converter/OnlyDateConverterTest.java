package io.leopard.web.editor.converter;

import io.leopard.test.mock.MockTests;

import org.junit.Assert;
import org.junit.Test;

public class OnlyDateConverterTest extends MockTests {

	@Test
	public void convert() {
		OnlyDateConverter converter = new OnlyDateConverter();
		Assert.assertEquals("2013-01-01", converter.convert("2013-01-01 00:00:00").toString());
	}

}