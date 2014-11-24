package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.OldUbbUtil;

import org.junit.Assert;
import org.junit.Test;

public class OldUbbUtilTest {

	@Test
	public void OldUbbUtil() {
		new OldUbbUtil();
	}

	@Test
	public void parse() {
		Assert.assertNull(OldUbbUtil.parse(null));
		Assert.assertEquals("&lt;a&gt;", OldUbbUtil.parse("<a>"));
	}

}