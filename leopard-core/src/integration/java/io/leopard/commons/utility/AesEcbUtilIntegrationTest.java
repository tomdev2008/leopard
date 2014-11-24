package io.leopard.commons.utility;

import org.junit.Assert;

import org.junit.Test;

public class AesEcbUtilIntegrationTest {

	@Test
	public void encrypt() {
		String key = "1234567890abcdef";
		String data = "data";
		// 密文:g7VVaieElN7Yx7Mhv8vQRA==

		String encrypted = AesEcbUtil.encrypt(data, key);
		System.out.println("encrypted:" + encrypted);
		String str = AesEcbUtil.decrypt(encrypted, key);

		Assert.assertEquals(data, str);
	}

	@Test
	public void decrypt() {
		String encrypted = "A25YXrGMUPLFUG304+45Gw==";
		String key = "1234567890abcdef";
		String data = AesEcbUtil.decrypt(encrypted, key);
		System.out.println("data:" + data);
	}

	@Test
	public void makeKey() {
		String key = AesEcbUtil.makeKey();
		System.out.println("key:" + key);
	}

}