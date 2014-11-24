package io.leopard.util;

import io.leopard.util.BloomFilter;

import org.junit.Test;

public class BloomFilterIntegrationTest {

	@Test
	public void BloomFilter() {
		BloomFilter bloomFilter = new BloomFilter(1000000);
		for (int i = 0; i < 10; i++) {
			bloomFilter.put(i + "");
		}
		for (int i = 0; i < 20; i++) {
			System.out.println(i + ":" + bloomFilter.contains(i + ""));
		}

	}

}