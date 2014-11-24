package io.leopard.util;

import io.leopard.commons.utility.DateUtil;
import io.leopard.util.Pager;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;

import org.junit.Test;

public class PagerTest {
	private static Map<String, Object> params = new LinkedHashMap<String, Object>();
	static {
		params.put("key1", "value1");
		params.put("key2", "value2");
	}
	protected Pager pager = new Pager(30, 2, 10, "url", params);

	@Test
	public void Pager() {
		// AUTO
	}

	@Test
	public void getCurrentPage() {
		Assert.assertEquals(2, pager.getCurrentPage());
	}

	@Test
	public void getNextPage() {
		Assert.assertEquals(3, pager.getNextPage());
	}

	@Test
	public void getTotalPage() {
		Assert.assertEquals(3, pager.getTotalPage());
	}

	@Test
	public void getPrevPage() {
		Assert.assertEquals(1, pager.getPrevPage());
	}

	@Test
	public void getSize() {
		Assert.assertEquals(10, pager.getSize());
	}

	@Test
	public void getFirst() {
		Assert.assertEquals(10, pager.getFirst());
	}

	@Test
	public void setCurrentPage() {
		Pager pager = new Pager(30, 2, 10, "url", null);
		pager.setCurrentPage(1);
		Assert.assertEquals(1, pager.getCurrentPage());
	}

	@Test
	public void setSizeParamName() {
		Pager pager = new Pager(30, 2, 10, "url", null);
		pager.setSizeParamName("paramName");
		Assert.assertEquals("paramName", pager.getSizeParamName());
	}

	@Test
	public void setPageParamName() {
		Pager pager = new Pager(30, 2, 10, "url", null);
		pager.setPageParamName("paramName");
		Assert.assertEquals("paramName", pager.getPageParamName());
	}

	@Test
	public void formatDate() {
		String value = pager.formatDate(DateUtil.toDate("2013-01-01 00:00:00"));
		Assert.assertEquals("2013-01-01 00:00:00", value);
	}

	@Test
	public void getTotalCount() {
		Assert.assertEquals(30, pager.getTotalCount());
	}

	@Test
	public void setTotalPage() {
		Pager pager = new Pager(30, 2, 10, "url", null);
		pager.setTotalPage(1);
		Assert.assertEquals(1, pager.getTotalPage());
	}

	@Test
	public void setSize() {
		Pager pager = new Pager(30, 2, 10, "url", null);
		pager.setSize(5);
		Assert.assertEquals(5, pager.getSize());
	}

	@Test
	public void getUrl() {
		Assert.assertEquals("url?key1=value1&key2=value2&page=1&size=10", this.pager.getUrl(1, 10));
	}

	@Test
	public void getFirstUrl() {
		Assert.assertEquals("url?key1=value1&key2=value2&page=1&size=10", this.pager.getFirstUrl());
	}

	@Test
	public void getLastUrl() {
		Assert.assertEquals("url?key1=value1&key2=value2&page=3&size=10", this.pager.getLastUrl());
	}

	@Test
	public void getNextUrl() {
		Assert.assertEquals("url?key1=value1&key2=value2&page=3&size=10", this.pager.getNextUrl());
	}

	@Test
	public void getPreUrl() {
		Assert.assertEquals("url?key1=value1&key2=value2&page=1&size=10", this.pager.getPreUrl());

	}

}