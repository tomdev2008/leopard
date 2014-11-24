package io.leopard.commons.utility;

import org.junit.Test;

public class AESUtilIntegrationTest {

	@Test
	public void encrypt() {
		String publickey = "EB4E434E96C60DEE7CBAF92088307CDC";
		String encode = AESUtil.encrypt("str", publickey);
		System.out.println("encode:" + encode);

		String decode = AESUtil.decrypt(encode, publickey);
		System.out.println("decode:" + decode);
	}

	@Test
	public void decrypt() {
		String encrypted = "wrLDq24sw6YTwrlJwoPDoMKCLhhIHnw=";
		String key = "1234567890abcdef";
		String data = AESUtil.decrypt(encrypted, key);
		System.out.println("data:" + data);
	}

}