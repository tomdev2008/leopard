package io.leopard.monitor.alarm;

import org.junit.Assert;
import org.junit.Test;

public class AlarmUtilTest {

	@Test
	public void AlarmUtil() {
		new AlarmUtil();
	}

	@Test
	public void removeUseless() {
		Assert.assertEquals("非法用户名.", AlarmUtil.removeUseless("非法用户名[Abc]."));
	}

	// @Test
	// public void getStackTrace() {
	// String str = AlarmUtil.getStackTrace(null, new Exception());
	// System.out.println(str);
	// }

}