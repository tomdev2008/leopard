package io.leopard.web4j.permission.config;

import io.leopard.burrow.io.IOUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ IOUtil.class })
public class ConfigFileTest {

	@Test
	public void getContent() throws IOException {
		ConfigFile configFile = Mockito.spy(new ConfigFile());
		Mockito.doReturn("content").when(configFile).getContent("/permission.xml");
		Assert.assertEquals("content", configFile.getContent());
	}

	// @Test
	// public void getContent2() throws IOException {
	// ConfigFile configFile = Mockito.spy(new ConfigFile());
	// PowerMockito.when(IOUtil.readByClassPath("filename")).thenReturn("content");
	// Assert.assertEquals("content", configFile.getContent("filename"));
	// }
	//
	// @Test
	// public void getSubContent2() throws IOException {
	// ConfigFile configFile = Mockito.spy(new ConfigFile());
	// PowerMockito.when(IOUtil.readByClassPath("filename")).thenReturn("<import resource=\"filename\" />");
	//
	// Mockito.doReturn("subcontent").when(configFile).getSubContent("filename");
	// Assert.assertEquals("subcontent", configFile.getContent("filename"));
	//
	// Mockito.doReturn(null).when(configFile).getSubContent("filename");
	// Assert.assertEquals("", configFile.getContent("filename"));
	// }

	@Test
	public void getSubContent() throws IOException {
		ConfigFile configFile = Mockito.spy(new ConfigFile());

		Mockito.doReturn(null).when(configFile).getContent("filename");
		Assert.assertNull(configFile.getSubContent("filename"));

		Mockito.doReturn("content").when(configFile).getContent("filename");
		Assert.assertEquals("content", configFile.getSubContent("filename"));
	}

}