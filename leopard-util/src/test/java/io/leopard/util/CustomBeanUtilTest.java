package io.leopard.util;

import io.leopard.util.CustomBeanUtil;

import org.junit.Assert;
import org.junit.Test;

public class CustomBeanUtilTest {

	@Test
	public void isCustomBean() {
		Assert.assertTrue(CustomBeanUtil.isCustomBean(CustomBeanUtilTest.class));
	}

	@Test
	public void CustomBeanUtil() {
		new CustomBeanUtil();
	}

}