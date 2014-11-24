package io.leopard.commons.utility;

import io.leopard.commons.utility.Base16;
import io.leopard.commons.utility.Clock;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class Base16IntegrationTest {

	@Test
	public void encode() {
		String encode = "312c2c323031332d31302d31362032303a31333a33362c2c41374430453835323731393142443831304446433833393842333845423532433230363833433034";
		int size = 10000 * 100;
		Clock clock = Clock.start(size);
		for (int i = 0; i < size; i++) {
			Base16.decode(encode);
		}
		clock.avg("decode");

	}

	@Test
	public void decode() {
		String str = "3131383435313838352c2c323031332d31302d32322031363a34303a30352c2c38433645303442324446333537333045393944343142384246454533313437394237444637333744";
		String encode = Base16.encode(str);
		int size = 10000 * 100;
		Clock clock = Clock.start(size);
		for (int i = 0; i < size; i++) {
			Base16.decode(encode);
		}
		clock.avg("decode");
		// decode avg:447427 time:2235
	}

	@Test
	public void test() {
		String str = "3832323936303936382C2C323031332D31302D32322031393A33303A33362C2C34343132353934393737424446453444393745324237423138454335324533324231374432343733";
		String content = Base16.decode(str);
		System.out.println("content:" + content);
	}

	@Test
	public void lowerCase() {
		int size = 10000 * 10000;
		Clock clock = Clock.start(size);
		for (int i = 0; i < size; i++) {
			StringUtils.lowerCase("DDT");
		}
		clock.avg("lowerCase");
	}
}