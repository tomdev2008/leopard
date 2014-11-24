package io.leopard.util.ubb;

import io.leopard.util.ubb.FeijipiaoUbb;
import org.junit.Assert;

import org.junit.Test;

public class FeijipiaoUbbTest {

	@Test
	public void parse() {
		String content = "[fjp='yy://yxdt-[key=yg0vi8-txjivgameivs10&from=from_web]/']测试地址[/fjp]";
		String html = new FeijipiaoUbb("YyGameClient").parse(content);
		Assert.assertEquals("<a href=\"javascript:void(0)\" onclick=\"YyGameClient.openFeijipiao('yy://yxdt-[key=yg0vi8-txjivgameivs10&from=from_web]/');return false;\">测试地址</a>", html);
		// <a href="javascript:void(0)" onclick="YyGameClient.openYYUrl('yy://yxdt-[key=yg0vi8-txjivgameivs10&from=from_web]/');return false;">测试地址</a>
	}

}