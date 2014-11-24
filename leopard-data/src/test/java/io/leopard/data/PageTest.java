package io.leopard.data;

import io.leopard.commons.utility.ListUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class PageTest {

	@Test
	public void Page() {
		List<Integer> data = ListUtil.makeIntList("1,2");
		Page<Integer> page = new Page<Integer>();
		page.setCount(1);
		page.setData(data);
		Assert.assertEquals(1, page.getCount());
		Assert.assertEquals("1,2", StringUtils.join(page.getData(), ","));

		//
		Assert.assertEquals(1, Page.getCount(page));
		Assert.assertEquals("1,2", StringUtils.join(Page.getData(page), ","));

		Assert.assertEquals(0, Page.getCount(null));
		Assert.assertNull(Page.getData(null));
	}

}