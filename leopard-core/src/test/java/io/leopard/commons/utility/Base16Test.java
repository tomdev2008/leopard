package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class Base16Test {

	@Test
	public void encode() {
		Assert.assertEquals("31", Base16.encode("1"));
		Assert.assertEquals("313233343536", Base16.encode("123456"));
	}

	@Test
	public void decode() {
		Assert.assertEquals("1", Base16.decode("31"));
		Assert.assertEquals("123456", Base16.decode("313233343536"));
	}

	@Test
	public void Base16() {
		new Base16();
	}
}