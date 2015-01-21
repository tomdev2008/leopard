package io.leopard.util;

import io.leopard.util.BloomFilter.HashFunctions;

import org.junit.Assert;
import org.junit.Test;

public class BloomFilterTest {
	BloomFilter filter = new BloomFilter(20);

	@Test
	public void put() {
		filter.put("str");
		Assert.assertTrue(filter.contains("str"));
		Assert.assertFalse(filter.contains("str1"));
	}

	@Test
	public void getFalsePositiveProbability() {
		this.filter.getFalsePositiveProbability();
	}

	@Test
	public void HashFunctions() {
		new HashFunctions();
	}

}