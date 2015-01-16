package io.leopard.test.internal;

import io.leopard.data4j.env.ClassLoaderUtil;
import io.leopard.test.hosts.DnsConfig;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ClassLoaderUtil.class, DnsConfig.class })
public class TestContextLoaderTest {

	@Test
	public void getModuleConfig() {
		{
			PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news/target/");
			TestContextLoader loader = new TestContextLoader();
			Assert.assertEquals("/leopard-test/applicationContext-default.xml", loader.getModuleConfig());
		}
		{
			PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-dao/target/");
			TestContextLoader loader = new TestContextLoader();
			Assert.assertEquals("/leopard-test/applicationContext-dao.xml", loader.getModuleConfig());
		}
		{
			PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-admin/target/");
			TestContextLoader loader = new TestContextLoader();
			try {
				loader.getModuleConfig();
				Assert.fail("怎么没有抛异常?");
			}
			catch (RuntimeException e) {

			}
		}
	}

	@Test
	public void processLocations() {

	}

	@Test
	public void loadContext() throws Exception {
		TestContextLoader loader = new TestContextLoader();
		loader.loadContext("/leopard-test/annotation-config.xml");

		Assert.assertNotNull(loader.processLocations(String.class, "/leopard-test/annotation-config.xml"));
	}

	@Test
	public void createApplicationContext() {

	}

	// @Test
	// public void getFiles() {
	// PowerMockito.when(ClassLoaderUtil.getCurrentPath()).thenReturn("file:/D:/work/news/news-dao/target/");
	// TestContextLoader loader = Mockito.spy(new TestContextLoader());
	// Assert.assertEquals("a,b", StringUtils.join(loader.getFiles("a", "b"),
	// ","));
	//
	// // String[] files = loader.getFiles();
	//
	// Assert.assertEquals("/leopard-test/applicationContext-dao.xml",
	// StringUtils.join(loader.getFiles(), ","));
	// Mockito.doReturn(true).when(loader).entryExist("/integrationTest.xml");
	// Assert.assertEquals("/integrationTest.xml",
	// StringUtils.join(loader.getFiles(), ","));
	// // System.out.println("files:" + StringUtils.join(files, ","));
	// }
	//
	// @Test
	// public void entryExist() {
	// TestContextLoader loader = new TestContextLoader();
	// Assert.assertFalse(loader.entryExist("/integrationTest1.xml"));
	// Assert.assertTrue(loader.entryExist("/leopard-test/applicationContext-dao.xml"));
	// Assert.assertTrue(loader.entryExist("/leopard-test/applicationContext-service.xml"));
	// }
}