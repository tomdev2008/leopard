package io.leopard.web4j.permission.config;

import io.leopard.burrow.lang.XmlUtils;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ XmlUtils.class, ConfigFile.class })
public class ConfigTest {
	protected Config config = Mockito.spy(new Config());

	@Test
	public void getConfig() throws IOException {
		{
			Mockito.doReturn(null).when(config).getRootElement();
			Mockito.doReturn(null).when(config).parseConfig(null);
			Assert.assertNotNull(config.getConfig());
			Assert.assertNotNull(config.getConfig());
		}
	}

	@Test
	public void getConfig2() throws IOException {
		Config config = Mockito.spy(new Config());
		{
			Mockito.doThrow(new RuntimeException()).when(config).getRootElement();
			Assert.assertNotNull(config.getConfig());
		}

	}

	@Test
	public void getPermissionList() {
		config.getPermissionList();
	}

	@Test
	public void parseConfig() {
		Element root = Mockito.mock(Element.class);
		Element element1 = Mockito.mock(Element.class);
		List<Element> list = new ArrayList<Element>();
		list.add(element1);
		Mockito.doReturn(list).when(root).elements();

		Config config = Mockito.spy(new Config());
		{
			Mockito.doReturn("allow").when(element1).getName();
			try {
				config.parseConfig(root);
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
		}
		{
			Mockito.doReturn(new ArrayList<Element>()).when(element1).elements();
			Mockito.doReturn("permission1").when(element1).getName();

			Assert.assertNull(config.parseConfig(root));

		}
		{
			Mockito.doReturn(new ArrayList<Element>()).when(element1).elements();
			Mockito.doReturn("permission").when(element1).getName();
			Mockito.doNothing().when(config).addConfig(Mockito.any(Element.class));
			Assert.assertNull(config.parseConfig(root));

		}
	}

	@Test
	public void getAttribute() {
		Element element = Mockito.mock(Element.class);
		Assert.assertNull(config.getAttribute(element, "name"));

		Attribute attribute = Mockito.mock(Attribute.class);
		Mockito.doReturn("value").when(attribute).getValue();
		Mockito.doReturn(attribute).when(element).attribute("name");
		Assert.assertEquals("value", config.getAttribute(element, "name"));
	}

	@Test
	public void getException() {
		Element element = Mockito.mock(Element.class);
		Mockito.doReturn("exceptionName").when(config).getAttribute(element, "type");
		// Assert.assertEquals("exceptionName", config.getException(element));

		Mockito.doReturn("io.leopard.core.exception.InvalidException").when(config).getAttribute(element, "type");
		// Assert.assertEquals("io.leopard.core.exception.InvalidException",
		// config.getException(element));
	}

	@Test
	public void isLog() {
		Assert.assertTrue(config.isLog("true"));
		Assert.assertFalse(config.isLog("false"));
	}

	@Test
	public void getAllowList() {
		Element root = Mockito.mock(Element.class);
		{
			Mockito.doReturn(null).when(root).elements();
			Assert.assertNull(config.getAllowList(root));
		}
	}

	@Test
	public void getAllowList2() {
		Element root = Mockito.mock(Element.class);
		{
			Element element1 = Mockito.mock(Element.class);
			List<Element> list = new ArrayList<Element>();
			list.add(element1);
			Mockito.doReturn("ok").when(element1).getName();
			Mockito.doReturn(list).when(root).elements();
			try {
				config.getAllowList(root);
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
		}
	}

	@Test
	public void getAllowList3() {
		Element root = Mockito.mock(Element.class);
		{
			Element element1 = Mockito.mock(Element.class);
			List<Element> list = new ArrayList<Element>();
			list.add(element1);
			Mockito.doReturn("allow").when(element1).getName();
			// Mockito.doReturn("RuntimeException").when(config).getException(element1);
			Mockito.doReturn("RuntimeException").when(config).getAttribute(element1, "type");
			Mockito.doReturn("-599").when(config).getAttribute(element1, "statusCode");
			Mockito.doReturn("true").when(config).getAttribute(element1, "log");

			Mockito.doReturn(list).when(root).elements();
			List<Allow> allowList = config.getAllowList(root);

			Assert.assertEquals(1, allowList.size());
			// Assert.assertEquals(-599, allowList.get(0).getStatusCode());
		}
	}

	@Test
	public void addConfig() {
		Config config = Mockito.spy(new Config());
		// String id = this.getAttribute(element, "id");
		// String url = this.getAttribute(element, "url");
		// String page = this.getAttribute(element, "page");
		// String parent = this.getAttribute(element, "parent");
		Element element = Mockito.mock(Element.class);
		Mockito.doReturn("id").when(config).getAttribute(element, "id");
		Mockito.doReturn("url").when(config).getAttribute(element, "url");
		Mockito.doReturn("page").when(config).getAttribute(element, "page");
		Mockito.doReturn("parent").when(config).getAttribute(element, "parent");

		{
			Mockito.doReturn("url").when(config).getAttribute(element, "url");
			try {
				config.addConfig(element);
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
		}
		Mockito.doReturn("/user/").when(config).getAttribute(element, "url");
		Mockito.doReturn("WEBSERVICE").when(config).getAttribute(element, "page");
		Mockito.doReturn(null).when(config).getAllowList(element);
		config.beanList = new ArrayList<Bean>();
		config.addConfig(element);
	}
}