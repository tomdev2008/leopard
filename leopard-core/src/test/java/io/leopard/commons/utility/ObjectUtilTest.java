package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class ObjectUtilTest {

	@Test
	public void isNull() {
		Assert.assertTrue(ObjectUtil.isNull(null));
		Assert.assertFalse(ObjectUtil.isNull(""));
	}

	@Test
	public void isNotNull() {
		Assert.assertFalse(ObjectUtil.isNotNull(null));
		Assert.assertTrue(ObjectUtil.isNotNull(""));
	}

}