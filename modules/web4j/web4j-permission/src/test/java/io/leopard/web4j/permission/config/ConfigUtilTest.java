package io.leopard.web4j.permission.config;

import io.leopard.web4j.permission.config.Bean;
import io.leopard.web4j.permission.config.Config;
import io.leopard.web4j.permission.config.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ConfigUtilTest {

	@Test
	public void ConfigUtil() {
		new ConfigUtil();
	}

	@Test
	public void getBean() {
		Config config = Mockito.spy(new Config());
		ConfigUtil.config = config;
		{
			Mockito.doReturn(null).when(config).getPermissionList();
			Assert.assertNull(ConfigUtil.getBean("url"));
		}

		{
			List<Bean> list = new ArrayList<Bean>();
			Bean bean = new Bean();
			bean.setUrl("/user/");
			list.add(bean);
			Mockito.doReturn(list).when(config).getPermissionList();
			Assert.assertNull(ConfigUtil.getBean("url"));
			Assert.assertNotNull(ConfigUtil.getBean("/user/get.do"));
		}
	}
}