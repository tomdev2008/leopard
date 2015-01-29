package io.leopard.web4j.permission.config;

import io.leopard.web4j.permission.config.Allow;
import io.leopard.web4j.permission.config.Bean;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class BeanTest {

	@Test
	public void getUrl() {
		Bean bean = new Bean();
		bean.setUrl("url");
		Assert.assertEquals("url", bean.getUrl());
		bean.setParent("parent");
		Assert.assertEquals("parent", bean.getParent());
		bean.setId("id");
		Assert.assertEquals("id", bean.getId());
		bean.setEnable(true);
		Assert.assertTrue(bean.isEnable());
		bean.setAllowList(new ArrayList<Allow>());
		Assert.assertEquals(0, bean.getAllowList().size());
	}

}