package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class PageUtilTest {

	@Test
	public void getStart() {
		Assert.assertEquals(0, PageUtil.getStart(1, 10));
		Assert.assertEquals(10, PageUtil.getStart(2, 10));
	}

	@Test
	public void PageUtil() {
		new PageUtil();
	}

}