package io.leopard.commons.utility;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class FeiJiPiaoTest {

	@Test
	public void encode() {
		String encode = FeiJiPiao.encode("abc");
		String decode = FeiJiPiao.decode(encode);
		Assert.assertEquals("abc", decode);
	}

	@Test
	public void parse() {
		String encode = FeiJiPiao.encode("key1=value1&key2=value2");

		Map<String, String> param = FeiJiPiao.parse(encode);

		Assert.assertEquals(2, param.size());
		Assert.assertEquals("value1", param.get("key1"));
		Assert.assertEquals("value2", param.get("key2"));
	}

	@Test
	public void FeiJiPiao() {
		new FeiJiPiao();
	}

}