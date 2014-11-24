package io.leopard.monitor.url;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class UrlInfoServiceTest {

	@Test
	public void UrlInfoService() {
		new UrlInfoService();
	}

	@Test
	public void add() {
		UrlInfoService.add("/monitor/monitor.do");
		UrlInfoService.add("/index.do");
		UrlInfoService.add("/user/index.do");

		List<UrlInfo> list = UrlInfoService.list();
		Assert.assertEquals(2, list.size());
	}

}