package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class AESUtilTest {

	@Test
	public void encrypt() {
		String key = "12345678901234567890123456789012";
		String encode = AESUtil.encrypt("content", key);
		String decode = AESUtil.decrypt(encode, key);
		System.out.println("decode:" + decode);

		// try {
		// AESUtil.encrypt("abc", key);
		// Assert.fail("怎么没有抛异常?");
		// }
		// catch (RuntimeException e) {
		// // e.printStackTrace();
		// }
	}

	@Test
	public void makeKey() {
		String key = AESUtil.makeKey();
		System.out.println("key:" + key);
	}

	@Test
	public void AESUtil() {
		new AESUtil();
	}

	@Test
	public void hex2byte() {
		Assert.assertNull(AESUtil.hex2byte(null));
		Assert.assertNull(AESUtil.hex2byte("a"));
	}

	@Test
	public void decrypt() {
		String key = "12345678901234567890123456789012";
		try {
			AESUtil.decrypt("abc", key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {
			// e.printStackTrace();
		}
	}

	@Test
	public void getKey() {
		try {
			AESUtil.getKey("11");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}
}