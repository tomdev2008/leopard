package io.leopard.commons.utility;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class SetUtilTest {

	@Test
	public void diff() {

		Assert.assertEquals("[a, b]", SetUtil.diff(null, SetUtil.makeSet("a,b")).toString());
		Assert.assertEquals("[a, b]", SetUtil.diff(SetUtil.makeSet("a,b"), null).toString());

		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		set1.add("a");
		set1.add("b");
		set1.add("c");
		set2.add("c");
		set2.add("d");
		set2.add("f");

		Set<String> set = SetUtil.diff(set1, set2);
		System.out.println("set:" + set);

		Assert.assertEquals(4, set.size());
		Assert.assertFalse(set.contains("c"));
	}

	@Test
	public void inter() {
		Assert.assertNull(SetUtil.inter(null, SetUtil.makeSet("a,b")));
		Assert.assertNull(SetUtil.inter(SetUtil.makeSet("a,b"), null));

		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		set1.add("a");
		set1.add("b");
		set1.add("c");
		set2.add("c");
		set2.add("d");
		set2.add("f");

		Set<String> set = SetUtil.inter(set1, set2);
		Assert.assertEquals("[c]", set.toString());
	}

	@Test
	public void makeIntSet() {
		Assert.assertEquals("[1, 2, 3]", SetUtil.makeIntSet("1,2,3").toString());
	}

	@Test
	public void makeLongSet() {
		Assert.assertEquals("[1, 2, 3]", SetUtil.makeLongSet("1,2,3").toString());
	}

	@Test
	public void makeSet() {
		Assert.assertEquals("[1, 2, 3]", SetUtil.makeSet("1,2,3").toString());

		Assert.assertEquals("[a0, a1]", SetUtil.makeSet("a", 0, 2).toString());
	}

	@Test
	public void defaultSet() {
		Assert.assertEquals(0, SetUtil.defaultSet((Set<String>) null).size());
		Assert.assertEquals(3, SetUtil.defaultSet(SetUtil.makeSet("1,2,3")).size());
	}

	@Test
	public void size() {
		Assert.assertEquals(0, SetUtil.size((Set<String>) null));
		Assert.assertEquals(3, SetUtil.size(SetUtil.makeSet("1,2,3")));
	}

	@Test
	public void toArray() {
		Set<Integer> set = SetUtil.makeIntSet("1,2,3");
		String[] arr = SetUtil.toArray(set);
		Assert.assertEquals("1", arr[0]);
		Assert.assertEquals("2", arr[1]);
		Assert.assertEquals("3", arr[2]);
	}

	@Test
	public void toArray2() {
		Set<String> set = SetUtil.makeSet("1,2,3");
		String[] arr = SetUtil.toArray2(set);
		Assert.assertEquals("1", arr[0]);
		Assert.assertEquals("2", arr[1]);
		Assert.assertEquals("3", arr[2]);
	}

	@Test
	public void isEmpty() {
		Assert.assertTrue(SetUtil.isEmpty((Set<String>) null));
		Assert.assertTrue(SetUtil.isEmpty(new HashSet<String>()));
		Assert.assertFalse(SetUtil.isEmpty(SetUtil.makeSet("1,2,3")));
	}

	@Test
	public void isNotEmpty() {
		Assert.assertFalse(SetUtil.isNotEmpty((Set<String>) null));
		Assert.assertFalse(SetUtil.isNotEmpty(new HashSet<String>()));
		Assert.assertTrue(SetUtil.isNotEmpty(SetUtil.makeSet("1,2,3")));
	}

	@Test
	public void getFirstElement() {
		Assert.assertNull(SetUtil.getFirstElement(null));
		Assert.assertEquals("a", SetUtil.getFirstElement(SetUtil.makeSet("a,b")));
	}

	@Test
	public void SetUtil() {
		new SetUtil();
	}

	@Test
	public void elementAt() {
		Assert.assertEquals("a", SetUtil.elementAt(SetUtil.makeSet("a,b"), 0));
		Assert.assertEquals("b", SetUtil.elementAt(SetUtil.makeSet("a,b"), 1));
	}
}