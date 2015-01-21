package io.leopard.util;

import org.junit.Assert;
import org.junit.Test;

public class ClassTypeUtilTest {

	@Test
	public void ClassTypeUtil() {
		new ClassTypeUtil();
	}

	@Test
	public void isInteger() {
		Assert.assertTrue(ClassTypeUtil.isInteger(int.class));
		Assert.assertTrue(ClassTypeUtil.isInteger(Integer.class));
		Assert.assertFalse(ClassTypeUtil.isInteger(String.class));
	}

	@Test
	public void isLong() {
		Assert.assertTrue(ClassTypeUtil.isLong(long.class));
		Assert.assertTrue(ClassTypeUtil.isLong(Long.class));
		Assert.assertFalse(ClassTypeUtil.isLong(String.class));
	}

	@Test
	public void isFloat() {
		Assert.assertTrue(ClassTypeUtil.isFloat(float.class));
		Assert.assertTrue(ClassTypeUtil.isFloat(Float.class));
		Assert.assertFalse(ClassTypeUtil.isFloat(String.class));
	}

	@Test
	public void isDouble() {
		Assert.assertTrue(ClassTypeUtil.isDouble(double.class));
		Assert.assertTrue(ClassTypeUtil.isDouble(Double.class));
		Assert.assertFalse(ClassTypeUtil.isDouble(String.class));
	}

	@Test
	public void isBoolean() {
		Assert.assertTrue(ClassTypeUtil.isBoolean(Boolean.class));
		Assert.assertTrue(ClassTypeUtil.isBoolean(boolean.class));
		Assert.assertFalse(ClassTypeUtil.isBoolean(String.class));
	}

}