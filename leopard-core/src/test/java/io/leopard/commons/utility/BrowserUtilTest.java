package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class BrowserUtilTest {

	@Test
	public void getIeVersion() {
		Assert.assertEquals(6, BrowserUtil.getIeVersion("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; GTB7.4)"));
		Assert.assertEquals(7, BrowserUtil.getIeVersion("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; SV1; GTB7.4)"));
	}

}