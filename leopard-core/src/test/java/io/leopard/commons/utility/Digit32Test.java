package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class Digit32Test {

	@Test
	public void toString2() {
		String str = Digit32.toString(10, 3);
		Assert.assertEquals("AAK", str);
		Assert.assertEquals(10, Digit32.toDecimal(str));
		// System.out.println("str:" + str);
	}

}