package io.leopard.test4j.mock.transaction;

import io.leopard.burrow.lang.Json;
import io.leopard.memcache.Memcache;
import io.leopard.memcache.MemcacheMemoryImpl;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;

public class MemdbTransactionImplTest {

	@Test
	public void get() {
		MemdbTransactionImpl memdb = new MemdbTransactionImpl();
		Memcache memcache = new MemcacheMemoryImpl();
//		LeopardMockito.setProperty(memdb, memcache);
		System.out.println("memdb:" + memdb.memcache);
		memdb.set("key", "value");
		Assert.assertEquals("value", memdb.get("key"));
		memdb.remove("key");
		Assert.assertNull(memdb.get("key"));
	}

	@Test
	public void get2() {
		MemdbTransactionImpl memdb = new MemdbTransactionImpl();
		Memcache memcache = new MemcacheMemoryImpl();
//		LeopardMockito.setProperty(memdb, memcache);
		memdb.set("key", Json.toJson("value"));
		Assert.assertEquals("value", memdb.get("key", String.class));
	}

	@Test
	public void dbSize() {
		MemdbTransactionImpl memdb = new MemdbTransactionImpl();

		try {
			memdb.dbSize();
			Assert.fail("怎么没有抛异常?");
		}
		catch (NotImplementedException e) {

		}
	}

}