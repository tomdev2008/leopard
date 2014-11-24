package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

//@SuppressWarnings("deprecation")
public class ArgsUtilTest {

	@Test
	public void arr2Str() {

		Assert.assertEquals("b,a", ArgsUtil.arr2Str(new String[] { "a", "b" }));
	}

	@Test
	public void str2List() {
		Assert.assertEquals("[a, b]", ArgsUtil.str2List("a,b").toString());
	}

	@Test
	public void str2Arr() {
		String[] arr = ArgsUtil.str2Arr("a,b");
		Assert.assertEquals("a", arr[0]);
		Assert.assertEquals("b", arr[1]);
	}

}