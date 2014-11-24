package io.leopard.util.ubb;

import io.leopard.util.ubb.UbbUtil;

import org.junit.Test;

public class UbbUtilTest {

	@Test
	public void parse() {
		String content = "[fjp='yy://yxdt-[key=yg0vi8-txjivgameivs10&from=from_web]/']测试地址[/fjp]";
		String html = UbbUtil.parse(content);
		System.out.println("html:" + html);
	}

	@Test
	public void UbbUtil() {
		new UbbUtil();
	}

}