package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class ResinTest {

	@Test
	public void isResin() {
		Assert.assertFalse(Resin.isResin());
	}

	@Test
	public void Resin() {
		new Resin();
	}

	@Test
	public void setShortThreadName() {
		Resin.setShortThreadName();
	}

}