package io.leopard.data4j.memdb;

import io.leopard.burrow.lang.SynchronizedLRUMap;

public class MemdbLruImpl extends MemdbImpl implements Memdb {

	public MemdbLruImpl(int maxEntries) {
		data = new SynchronizedLRUMap<String, String>(10, maxEntries);
	}

}
