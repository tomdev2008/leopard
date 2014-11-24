package io.leopard.monitor;

import org.junit.Assert;
import org.junit.Test;

public class CapacityUtilTest {

	@Test
	public void human() {
		// getJvmUsedMemory:61211688 getJvmInitMemory:92249984

		Assert.assertEquals("58M", CapacityUtil.human(61211688));
		Assert.assertEquals("87M", CapacityUtil.human(92249984));
		Assert.assertEquals("1023B", CapacityUtil.human(1023));
		Assert.assertEquals("1K", CapacityUtil.human(1024));
		Assert.assertEquals("1K", CapacityUtil.human(1025));
		Assert.assertEquals("1024M", CapacityUtil.human(1024 * 1024 * 1024 + 1));

	}

	@Test
	public void parse() {
		Assert.assertEquals(0, CapacityUtil.parse(""));
		try {
			CapacityUtil.parse("1");
			Assert.fail("为什么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			CapacityUtil.parse("1T");
			Assert.fail("为什么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		Assert.assertEquals(1024, CapacityUtil.parse("1K"));
		Assert.assertEquals(2 * 1024 * 1024, CapacityUtil.parse("2M"));
		Assert.assertEquals(2L * 1024 * 1024 * 1024, CapacityUtil.parse("2G"));
	}

	@Test
	public void CapacityUtil() {
		new CapacityUtil();
	}

	// @Test
	// public void modifyUnit() {
	// Assert.assertEquals(1, CapacityUtil.modifyUnit(1024, "1K"));
	// Assert.assertEquals(1024, CapacityUtil.modifyUnit(1024 * 1024 + 1, "1K"));
	// Assert.assertEquals(1, CapacityUtil.modifyUnit(1024 * 1024 + 1, "1M"));
	// }

	@Test
	public void toMega() {
		Assert.assertEquals(1, CapacityUtil.toMega(1024 * 1024));
	}

}