package io.leopard.test.kit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class MapThreadSafetyAssertTest {

	@Test
	public void check() {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		MapThreadSafetyAssert.check(map, 1, 10);
	}
}