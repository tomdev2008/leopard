package io.leopard.commons.utility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Test;

public class SystemUtilTest {

	@Test
	public void getStackMessage() {
		Assert.assertNull(SystemUtil.getStackMessage(null));
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();

		String message = SystemUtil.getStackMessage(stacks);
		// System.out.println("message:" + message);
		Assert.assertTrue(message.startsWith("io.leopard.commons.utility.SystemUtilTest.getStackMessage"));

	}

	@Test
	public void currentTimeMillis() {
		Assert.assertTrue(SystemUtil.currentTimeMillis() > 0);
	}

	@Test
	public void sleep() {
		SystemUtil.sleep(10);
	}

	@Test
	public void execShell() {
		SystemUtil.execShell("ls");
	}

	@Test
	public void execShell2() {
		String message = SystemUtil.execShell2("ls");
		Assert.assertTrue(StringUtils.isNotEmpty(message));
	}

	@Test
	public void isNotLinux() {
		if (SystemUtils.IS_OS_LINUX) {
			Assert.assertFalse(SystemUtil.isNotLinux());
		}
		else {
			Assert.assertTrue(SystemUtil.isNotLinux());
		}
	}

	@Test
	public void isNotWindows() {
		if (SystemUtils.IS_OS_WINDOWS) {
			Assert.assertFalse(SystemUtil.isNotWindows());
		}
		else {
			Assert.assertTrue(SystemUtil.isNotWindows());
		}
	}

	@Test
	public void isWindows() {
		if (SystemUtils.IS_OS_WINDOWS) {
			Assert.assertTrue(SystemUtil.isWindows());
		}
		else {
			Assert.assertFalse(SystemUtil.isWindows());
		}
	}

}