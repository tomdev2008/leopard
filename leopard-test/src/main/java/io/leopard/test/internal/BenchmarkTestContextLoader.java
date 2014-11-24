package io.leopard.test.internal;

import io.leopard.commons.utility.ArrayUtil;

import org.springframework.context.ApplicationContext;

public class BenchmarkTestContextLoader extends TestContextLoader {
	@Override
	protected ApplicationContext createApplicationContext(String[] locations) {
		System.err.println("createApplicationContext:" + locations);
		String[] files = ArrayUtil.insertFirst(locations, "/leopard-test/benchmark-test.xml");
		return super.createApplicationContext(files);
	}
}
