package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class AesEcbUtilTest {

	@Test
	public void encrypt() {
		String key = "1234567890123456";

		String encode = AesEcbUtil.encrypt("123", key);
		String decode = AesEcbUtil.decrypt(encode, key);

		System.out.println("[" + decode + "]");
		Assert.assertEquals("123", decode);
	}

}