package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class AssertDataTest {

	@Test
	public void notEmpty() {
		AssertData.notEmpty("str", "不能为空.");
		try {
			AssertData.notEmpty("", "不能为空.");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void assertTrue() {
		AssertData.assertTrue(true, "怎么不为true?");
		try {
			AssertData.assertTrue(false, "怎么不为true?");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

}