package io.leopard.test.internal;

import org.junit.Test;

public class BenchmarkTestContextLoaderTest {

	@Test
	public void createApplicationContext() {
		new BenchmarkTestContextLoader().createApplicationContext(new String[] { "/leopard-test/annotation-config.xml" });

	}

}