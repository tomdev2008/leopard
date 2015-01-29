package io.leopard.web4j.permission.config;

import io.leopard.web4j.permission.config.ConfigParent;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ConfigParentTest {

	@Test
	public void parseBean() {
		ConfigParent configParent = Mockito.spy(new ConfigParent(""));
		String body = "<permission parent=\"parent1\"></permission>";

		{
			Mockito.doReturn("body").when(configParent).getBody("parent1");
			String bean = configParent.parseBean(body);
			Assert.assertTrue(bean.indexOf("parent1\">body") != -1);
		}
		{
			Mockito.doReturn("").when(configParent).getBody("parent1");
			Assert.assertEquals(body, configParent.parseBean(body));
		}
	}

	@Test
	public void parseBean2() {
		ConfigParent configParent = Mockito.spy(new ConfigParent(""));
		Mockito.doReturn("body").when(configParent).getBody("parent1");
		Assert.assertEquals("", configParent.parseBean(""));
	}

	@Test
	public void getBody() {
		{
			ConfigParent configParent = new ConfigParent("");
			Assert.assertNull(configParent.getBody("pid"));
		}
		{
			String content = "<permission id=\"pid\">body</permission>";
			ConfigParent configParent = new ConfigParent(content);
			Assert.assertEquals("body", configParent.getBody("pid"));
		}
	}

	@Test
	public void parse() {
		String content = "<permission id=\"pid\">body</permission>";
		ConfigParent configParent = Mockito.spy(new ConfigParent(content));
		Mockito.doReturn("body").when(configParent).parseBean(Mockito.anyString());
		String result = configParent.parse();
		// System.out.println("result:" + result);
		Assert.assertEquals("body", result);
	}

	@Test
	public void getContent() {
		ConfigParent configParent = Mockito.spy(new ConfigParent(""));
		Mockito.doReturn("body").when(configParent).parse();
		Assert.assertEquals("body", configParent.getContent());
	}
}