package io.leopard.test.internal;

import io.leopard.data.env.ClassLoaderUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ClassLoaderUtil.class })
public class ModuleParserTest {

	@Test
	public void setPath() {
		PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-dao/target/");

		ModuleParser moduleParser = new ModuleParser();
		{
			moduleParser.setPath("/work/news/news/news-dao");
			Assert.assertTrue(moduleParser.isModule());
			Assert.assertEquals("dao", moduleParser.getModuleName());
		}
		{
			moduleParser.setPath("/work/news/news");
			Assert.assertFalse(moduleParser.isModule());
		}
	}

	@Test
	public void parsePath() {
		PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-dao/target/");
		ModuleParser moduleParser = new ModuleParser();
		String path = moduleParser.parsePath();
		System.out.println("path:" + path);
		Assert.assertEquals("/work/news/news-dao", path);
	}

	@Test
	public void parseFolder() {
		PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-dao/target/");

		ModuleParser moduleParser = new ModuleParser();
		Assert.assertEquals("news", moduleParser.parseFolder("/news"));
		Assert.assertEquals("news-dao", moduleParser.parseFolder("/news-dao"));

		try {
			moduleParser.parseFolder("/news-dao/");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			moduleParser.parseFolder("news-dao");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void getModuleName() {
		PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-dao/target/");
		ModuleParser moduleParser = new ModuleParser();
		Assert.assertEquals("dao", moduleParser.getModuleName());
	}

	@Test
	public void isModule() {
		PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-dao/target/");
		ModuleParser moduleParser = new ModuleParser();
		Assert.assertTrue(moduleParser.isModule());
	}

}