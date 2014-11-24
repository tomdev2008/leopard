package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class Base64Test {

	@Test
	public void encode() {
		Assert.assertNull(Base64.encode(null));
		Assert.assertEquals("", Base64.encode(""));
		Assert.assertEquals("5L2g5aW9", Base64.encode("你好"));
	}

	@Test
	public void decode() {
		Assert.assertNull(Base64.decode(null));
		Assert.assertEquals("", Base64.decode(""));
		Assert.assertEquals("你好", Base64.decode("5L2g5aW9"));
	}

}