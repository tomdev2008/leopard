package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class UdbConstantTest {

	@Test
	public void getBytes() {
		Assert.assertEquals(0, UdbConstant.getBytes(null));
		Assert.assertEquals(0, UdbConstant.getBytes(""));
		Assert.assertEquals(4, UdbConstant.getBytes("你好"));
		Assert.assertEquals(4, UdbConstant.getBytes("abcd"));
	}

	@Test
	public void isValidUsername() {
		Assert.assertTrue(UdbConstant.isValidUsername("　"));// 全角空格
		Assert.assertFalse(UdbConstant.isValidUsername(""));
		Assert.assertFalse(UdbConstant.isValidUsername("<ab"));
		Assert.assertFalse(UdbConstant.isValidUsername("ab>"));
		Assert.assertFalse(UdbConstant.isValidUsername("你好哈哈哈你好哈哈哈你好哈哈哈1"));// 31个字符
		Assert.assertTrue(UdbConstant.isValidUsername("你好哈哈哈你好哈哈哈你好哈哈哈"));
		Assert.assertFalse(UdbConstant.isValidUsername("@ahai"));
		Assert.assertFalse(UdbConstant.isValidUsername("Ahai"));
		Assert.assertFalse(UdbConstant.isValidUsername("a hai"));
		Assert.assertTrue(UdbConstant.isValidUsername("ahai"));
	}

	@Test
	public void UdbConstant() {
		new UdbConstant();
	}
}