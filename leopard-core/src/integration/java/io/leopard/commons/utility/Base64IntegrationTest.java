package io.leopard.commons.utility;

import io.leopard.commons.utility.Base64;
import io.leopard.commons.utility.Clock;

import org.junit.Test;

public class Base64IntegrationTest {

	@Test
	public void decode() {
		String str = "111111111111111111111111111";
		String encode = Base64.encode(str);
		int size = 10000 * 500;
		Clock clock = Clock.start(size);
		for (int i = 0; i < size; i++) {
			Base64.decode(encode);
		}
		clock.avg("decode");
	}

	@Test
	public void encode() {
		for (int i = 0; i < 10; i++) {
			System.out.println(Base64.encode("" + i));
		}
	}

}