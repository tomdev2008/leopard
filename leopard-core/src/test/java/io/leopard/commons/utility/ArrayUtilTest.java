package io.leopard.commons.utility;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilTest {

	@Test
	public void sub() {
		String[] args = new String[] { "a", "b", "c" };
		Object[] args2 = ArrayUtil.sub(args, 2);
		Assert.assertEquals(2, args2.length);
		Assert.assertEquals("a", args2[0]);
		Assert.assertEquals("b", args2[1]);
	}

	@Test
	public void removeLast() {
		String[] args = new String[] { "a", "b", "c" };
		Object[] args2 = ArrayUtil.removeLast(args);
		Assert.assertEquals(2, args2.length);
		Assert.assertEquals("a", args2[0]);
		Assert.assertEquals("b", args2[1]);
	}

	@Test
	public void removeFirst() {
		String[] args = new String[] { "a", "b", "c" };
		Object[] args2 = ArrayUtil.removeFirst(args);
		Assert.assertEquals(2, args2.length);
		Assert.assertEquals("b", args2[0]);
		Assert.assertEquals("c", args2[1]);
	}

	@Test
	public void insertFirst() {
		String[] args = new String[] { "b" };
		Object[] args2 = ArrayUtil.insertFirst(args, "a");
		Assert.assertEquals(2, args2.length);
		Assert.assertEquals("a", args2[0]);
		Assert.assertEquals("b", args2[1]);
	}

	@Test
	public void make() {
		String[] strs = ArrayUtil.make("pre", 10);
		for (int i = 0; i < 10; i++) {
			Assert.assertEquals("pre" + i, strs[i]);
		}
	}

	@Test
	public void ArrayUtil() {
		new ArrayUtil();
	}

	@Test
	public void append() {
		String[] values1 = new String[] { "a", "b" };
		String[] values2 = ArrayUtil.append(values1, "c");
		Assert.assertEquals("a, b, c", StringUtils.join(values2, ", "));
	}

}