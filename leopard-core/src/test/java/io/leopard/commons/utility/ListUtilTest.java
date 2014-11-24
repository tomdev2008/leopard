package io.leopard.commons.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ListUtilTest {

	@Test
	public void size() {

		Object[] objs = null;
		Assert.assertEquals(0, ListUtil.size(objs));

		Assert.assertEquals(0, ListUtil.size(new Object[] {}));
		Assert.assertEquals(1, ListUtil.size(new Object[] { "hctan" }));

		Assert.assertEquals(0, ListUtil.size(new long[] {}));
		Assert.assertEquals(1, ListUtil.size(new long[] { 1 }));
	}

	@Test
	public void size2() {

		List<String> list = null;
		Assert.assertEquals(0, ListUtil.size(list));
		list = new ArrayList<String>();
		Assert.assertEquals(0, ListUtil.size(list));
		list.add("hctan");
		Assert.assertEquals(1, ListUtil.size(list));

	}

	@Test
	public void uniq() {
		Assert.assertNull(ListUtil.uniq(null));
		List<String> list = ListUtil.makeList("1,2,3,4,1,2,3");
		Assert.assertEquals("[1, 2, 3, 4]", ListUtil.uniq(list).toString());

	}

	@Test
	public void isNotEmpty() {
		Assert.assertFalse(ListUtil.isNotEmpty(null));
		Assert.assertFalse(ListUtil.isNotEmpty(new ArrayList<String>()));

		Assert.assertTrue(ListUtil.isNotEmpty(ListUtil.makeList("1,2")));
	}

	@Test
	public void isEmpty() {
		Assert.assertTrue(ListUtil.isEmpty(null));
		Assert.assertTrue(ListUtil.isEmpty(new ArrayList<String>()));

		Assert.assertFalse(ListUtil.isEmpty(ListUtil.makeList("1,2")));
	}

	@Test
	public void defaultStrings() {
		Assert.assertEquals(0, ListUtil.defaultStrings(null).length);
		Assert.assertEquals(2, ListUtil.defaultStrings(new String[2]).length);
	}

	@Test
	public void defaultList() {
		Assert.assertEquals(0, ListUtil.defaultList(null).size());

		Assert.assertEquals(2, ListUtil.defaultList(ListUtil.makeList("1,2")).size());
	}

	@Test
	public void noNull() {
		Assert.assertNull(ListUtil.noNull(null));

		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add(null);
		list.add("b");

		Assert.assertEquals("[a, b]", ListUtil.noNull(list).toString());
	}

	@Test
	public void toList() {
		Assert.assertNull(ListUtil.toList(new HashSet<String>()));
		Assert.assertEquals("[a, b]", ListUtil.toList(SetUtil.makeSet("a,b")).toString());

		Assert.assertNull(ListUtil.toList((String) null));
		Assert.assertNull(ListUtil.toList(""));

		Assert.assertEquals("[a, b, c]", ListUtil.toList("a,b,c").toString());

		{
			Assert.assertNull(ListUtil.toList("", true));
			Assert.assertEquals("[a, b, c]", ListUtil.toList("a,b,c", false).toString());
			Assert.assertEquals("[a, b, c]", ListUtil.toList("a, b, c", true).toString());
		}
	}

	@Test
	public void toSet() {
		Assert.assertNull(ListUtil.toSet(null));

		List<String> list = ListUtil.makeList("a,b,b");
		Assert.assertEquals("[a, b]", ListUtil.toSet(list).toString());
	}

	@Test
	public void toIntSet() {
		Assert.assertNull(ListUtil.toIntSet(null));

		List<Integer> list = ListUtil.makeIntList("1,2,2");
		Assert.assertEquals("[1, 2]", ListUtil.toIntSet(list).toString());
	}

	@Test
	public void toIntList() {
		Assert.assertEquals("[1]", ListUtil.toIntList(1).toString());

		Assert.assertNull(ListUtil.toIntList((String) null));
		Assert.assertNull(ListUtil.toIntList(""));

		Assert.assertEquals("[1, 2, 3]", ListUtil.toIntList("1,2,3").toString());

		// set
		Assert.assertNull(ListUtil.toIntList((Set<String>) null));

		Assert.assertEquals("[1, 2]", ListUtil.toIntList(SetUtil.makeSet("1,2,2")).toString());

	}

	@Test
	public void toIntList2() {
		Assert.assertNull(ListUtil.toIntList(new ArrayList<String>()));
		Assert.assertEquals("[1, 2]", ListUtil.toIntList(ListUtil.makeList("1,2")).toString());
	}

	@Test
	public void ListUtil() {
		new ListUtil();
	}

	@Test
	public void getIntKeys() {
		String[] keys = ListUtil.getIntKeys("p", SetUtil.makeIntSet("1,2"));

		Assert.assertEquals("p:1", keys[0]);
		Assert.assertEquals("p:2", keys[1]);
	}

	@Test
	public void getKeys() {
		String[] keys = ListUtil.getKeys("p", SetUtil.makeSet("1,2"));

		Assert.assertEquals("p:1", keys[0]);
		Assert.assertEquals("p:2", keys[1]);
	}

	@Test
	public void toStringArray() {
		{
			Assert.assertNull(ListUtil.toStringArray((int[]) null));
			String[] strs = ListUtil.toStringArray(new int[] { 1, 2 });
			Assert.assertEquals("1", strs[0]);
			Assert.assertEquals("2", strs[1]);
		}
		{
			Assert.assertNull(ListUtil.toStringArray((List<Integer>) null));
			String[] strs = ListUtil.toStringArray(ListUtil.makeIntList("1,2"));
			Assert.assertEquals("1", strs[0]);
			Assert.assertEquals("2", strs[1]);
		}
	}

	@Test
	public void toArray() {
		Assert.assertNull(ListUtil.toArray(null));

		String[] strs = ListUtil.toArray(ListUtil.makeList("1,2"));
		Assert.assertEquals("1", strs[0]);
		Assert.assertEquals("2", strs[1]);
	}

	@Test
	public void makeLongList() {
		Assert.assertEquals("[1, 2]", ListUtil.makeLongList("1,2").toString());

		Assert.assertEquals("[1, 2]", ListUtil.makeLongList("1,2", ",").toString());
	}

	@Test
	public void makeList() {
		Assert.assertEquals("[p0, p1]", ListUtil.makeList("p", 0, 2).toString());
	}

	@Test
	public void toStringResult() {
		Assert.assertNull(ListUtil.toStringResult(null));
		Assert.assertNull(ListUtil.toStringResult(new ArrayList<String>()));
		Assert.assertEquals("[1, 2]", ListUtil.toStringResult(ListUtil.makeList("1,2")).toString());
	}

	@Test
	public void toStringList() {
		List<String> list = ListUtil.toStringList(ListUtil.makeIntList("1,2"));
		Assert.assertEquals("1", list.get(0));
		Assert.assertEquals("2", list.get(1));
	}

	@Test
	public void sub() {
		Assert.assertNull(ListUtil.sub(null, 2));
		Assert.assertEquals(0, ListUtil.sub(new ArrayList<String>(), 2).size());
		Assert.assertEquals(2, ListUtil.sub(ListUtil.makeList("1,2,3"), 2).size());
	}

	@Test
	public void removeAll() {
		List<String> list1 = ListUtil.makeList("1,2,3,4");
		List<String> list2 = ListUtil.makeList("1,2");
		List<String> list3 = ListUtil.removeAll(list1, list2);

		Assert.assertEquals("[3, 4]", list3.toString());
	}

	@Test
	public void makeDoubleList() {
		Assert.assertEquals("[0.1, 0.2]", ListUtil.makeDoubleList("0.1,0.2").toString());
		Assert.assertEquals("[0.1, 0.2]", ListUtil.makeDoubleList(0.1, 0.2).toString());
	}
}