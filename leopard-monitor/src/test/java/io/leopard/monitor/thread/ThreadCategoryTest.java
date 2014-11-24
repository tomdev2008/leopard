package io.leopard.monitor.thread;

import org.junit.Assert;
import org.junit.Test;

public class ThreadCategoryTest {

	@Test
	public void getKey() {
		Assert.assertEquals(80, (int) ThreadCategory.DAO.getKey());
	}

	@Test
	public void toEnumByDesc() {
		Assert.assertEquals(80, (int) ThreadCategory.toEnumByDesc("Dao").getKey());
		Assert.assertEquals("未知", ThreadCategory.toEnumByDesc("未知").getDesc());
		Assert.assertEquals("未知", ThreadCategory.toEnumByDesc("xxx").getDesc());
	}

	@Test
	public void ThreadCategory() {

	}

}