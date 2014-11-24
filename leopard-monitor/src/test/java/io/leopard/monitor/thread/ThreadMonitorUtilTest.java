package io.leopard.monitor.thread;

import org.junit.Assert;
import org.junit.Test;

public class ThreadMonitorUtilTest {

	@Test
	public void getCategory() {
		Assert.assertEquals("定时器", ThreadMonitorUtil.getCategory("io.leopard.util.timer.DayPeriod.sleep(DayPeriod.java:66)"));
		Assert.assertEquals("Service", ThreadMonitorUtil.getCategory("io.leopard.web.userinfo.service.LoginServiceImpl.validate(LoginServiceImpl.java:43)"));
		Assert.assertEquals("Dao", ThreadMonitorUtil.getCategory("io.leopard.news.dao.mysql.NewsDaoMysqlImpl.get(NewsDaoMysqlImpl.java:43)"));
		Assert.assertEquals("未知", ThreadMonitorUtil.getCategory("com.sun.XxxUtil.get(XxxUtil.java:43)"));
	}

	@Test
	public void ThreadMonitorUtil() {
		new ThreadMonitorUtil();
	}

	@Test
	public void isIgnoreThreadName() {
		Assert.assertTrue(ThreadMonitorUtil.isIgnoreThreadName("com.baidu.news.Test"));
		Assert.assertTrue(ThreadMonitorUtil.isIgnoreThreadName("xxx$$method"));
		Assert.assertTrue(ThreadMonitorUtil.isIgnoreThreadName("java.lang.String.method"));
		Assert.assertTrue(ThreadMonitorUtil.isIgnoreThreadName("java.util.TimerThread.mainLoop(Timer.java:509)"));

		Assert.assertTrue(ThreadMonitorUtil.isIgnoreThreadName("io.leopard.monitor.thread.A1"));
	}

	@Test
	public void isCompanyThreadName() {
		Assert.assertTrue(ThreadMonitorUtil.isCompanyThreadName("com.yy.news.Test"));
		Assert.assertTrue(ThreadMonitorUtil.isCompanyThreadName("cn.kuaikuai.news.Test"));
		Assert.assertFalse(ThreadMonitorUtil.isCompanyThreadName("com.baidu.news.Test"));
	}
}