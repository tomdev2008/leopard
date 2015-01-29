package io.leopard.data4j.memdb;

import org.junit.Assert;
import org.junit.Test;

public class MemdbLruImplTest {

	protected MemdbLruImpl memdb = new MemdbLruImpl(2);

	@Test
	public void get() {
		memdb.set("key1", "value1");
		memdb.set("key2", "value2");
		memdb.set("key3", "value3");
		Assert.assertNull(memdb.get("key1"));
		Assert.assertEquals("value3", memdb.get("key3"));
	}



}