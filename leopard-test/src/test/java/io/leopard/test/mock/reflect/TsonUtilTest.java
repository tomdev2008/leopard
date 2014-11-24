package io.leopard.test.mock.reflect;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class TsonUtilTest {

	@Test
	public void TsonUtil() {
		new TsonUtil();
	}

	@Test
	public void split() {
		Assert.assertEquals("a,b,c", StringUtils.join(TsonUtil.split("a,b,c", ','), ","));
		Assert.assertEquals("'a,','b','c'", StringUtils.join(TsonUtil.split("'a,','b','c'", ','), ","));
	}
}