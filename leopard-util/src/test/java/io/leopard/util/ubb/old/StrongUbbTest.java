package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.StrongUbb;
import org.junit.Assert;

import org.junit.Test;

public class StrongUbbTest {

	@Test
	public void getCALink() {
		Assert.assertEquals("<b>粗体</b>", StrongUbb.getCALink("[b]粗体[/b]"));
	}

	@Test
	public void StrongUbb() {
		new StrongUbb();
	}

	@Test
	public void strong() {
		String html = StrongUbb.strong("不论你是逆袭的屌丝还是日夜啪啪的高富帅，来游戏大厅参加撕牌夺宝活动，即可轻松获取超值大奖。游戏礼包、游戏直播白豌豆、月票、YY双倍积分免费拿！[url]&gt;&gt;立即参加,yy://yxdt-[key=yg0vi21-default&from=from_web][/url]");
		System.out.println("html:" + html);
	}

}